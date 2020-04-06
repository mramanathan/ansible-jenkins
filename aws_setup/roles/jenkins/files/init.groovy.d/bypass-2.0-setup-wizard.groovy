#!groovy

/*
 Skip the Jenkins 2.x setup wizard
*/

import jenkins.model.*

def instance = Jenkins.instance

legacySetupWizard = ('getSetupWizard' in instance.metaClass.methods*.name)
newSetupWizard = (('getInstallState' in instance.metaClass.methods*.name) && ('isSetupComplete' in instance.installState.metaClass.methods*.name))

if((!newSetupWizard && legacySetupWizard) || (newSetupWizard && !instance.installState.isSetupComplete())) {
    def wizard = instance.setupWizard
    if(wizard != null) {
        try {
          //pre Jenkins 2.6
          wizard.completeSetup(instance)
          PluginServletFilter.removeFilter(wizard.FORCE_SETUP_WIZARD_FILTER)
        } catch(Exception e) {
          wizard.completeSetup()
        }
        instance.save()
        println 'Jenkins 2.x wizard skipped.'
    }
}
