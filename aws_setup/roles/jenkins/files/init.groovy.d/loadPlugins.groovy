#!groovy

def jenkins_plugins = "git-client scm-api pipeline-restful-api ssh-slaves timestamper display-url-api pipeline-github bouncycastle-api docker-workflow pipeline-stage-view workflow-basic-steps pipeline-utility-steps docker-commons pipeline-build-step pipeline-model-definition mailer pipeline-model-extensions pipeline-milestone-step github lockable-resources workflow-support github-branch-source branch-api command-launcher pipeline-multibranch-defaults pipeline-model-api workflow-cps-global-lib workflow-scm-step pipeline-rest-api pipeline-input-step jsch jdk-tool workflow-job jquery-detached pipeline-stage-tags-metadata matrix-project pipeline-config-history credentials-binding github-api pipeline-stage-step credentials token-macro workflow-api workflow-aggregator plain-credentials pipeline-graph-analysis jackson2-api pipeline-model-declarative-agent ace-editor script-security durable-task git-server workflow-multibranch junit workflow-durable-task-step workflow-cps-global-lib-http structs pipeline-githubnotify-step git authentication-tokens pipeline-timeline cloudbees-folder handlebars config-file-provider pipeline-github-lib workflow-cps momentjs apache-httpcomponents-client-4-api pipeline-cps-http ssh-credentials workflow-step-api docker-build-step docker-build-publish resource-disposer ws-cleanup"

import jenkins.model.*
import java.util.logging.Logger

def logger = Logger.getLogger("")
def installed = false
def initialized = false
def pluginParameter="${jenkins_plugins}"
def plugins = pluginParameter.split()

logger.info("" + plugins)

def instance = Jenkins.getInstance()
def pm = instance.getPluginManager()
def uc = instance.getUpdateCenter()

plugins.each {
  logger.info("Checking " + it)
  if (!pm.getPlugin(it)) {
    logger.info("Looking UpdateCenter for " + it)
    if (!initialized) {
      uc.updateAllSites()
      initialized = true
    }
    def plugin = uc.getPlugin(it)
    if (plugin) {
      logger.info("Installing " + it)
    	def installFuture = plugin.deploy()
      while(!installFuture.isDone()) {
        logger.info("Waiting for plugin install: " + it)
        sleep(3000)
      }
      installed = true
    }
  }
}

if (installed) {
  logger.info("Plugins installed, initializing a restart!")
  instance.save()
  instance.restart()
}
