/*
 * Create the initial admin user
 * Inspiration from, https://github.com/geerlingguy/ansible-role-jenkins/issues/50
*/

#!groovy

import jenkins.model.*
import hudson.security.*

def instance = Jenkins.getInstance()

println "--> Creating the local user, 'admin'"

def hudsonRealm = new HudsonPrivateSecurityRealm(false)
hudsonRealm.createAccount('{{ jenkins_admin_username }}','{{ jenkins_admin_password }}')
instance.setSecurityRealm(hudsonRealm)

def strategy = new FullControlOnceLoggedInAuthorizationStrategy()
strategy.setAllowAnonymousRead(false)
instance.setAuthorizationStrategy(strategy)

instance.save()