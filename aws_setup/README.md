# What's available here?
Set of Ansible playbooks that will install bunch of basic utilities, install OpenJDK, prepare the system environment, and install Jenkins in an EC2 instance running Ubuntu 18.04.

## Pre-requisites
1. EC2 instance with Ubuntu 18.04
2. Access to the Internet from the EC2 instance
3. Python v2.7.15
4. Ansible v2.8.6

## Assumptions
- Jenkins will be installed as a package.
- Playbook will enable and start the Jenkins service.
- Post installation, the initial setup and configuration will have to be done manually, though.

## How to use the playbook?
```
git clone https://github.com/mramanathan/ansible-jenkins

cd aws_setup

ansible-playbook jenkins.yml
```

## What version of Jenkins will be installed?
2.176.3

And, this is configurable via the variable, `jenkins_version` in `jenkins.yml`

## Is it possible to customize the port?
YES, change the port number in the variable, `jenkins_port` in `jenkins.yml`

## How's the JDK handled?
`jdk` role is capable of installing OpenJDK 8.