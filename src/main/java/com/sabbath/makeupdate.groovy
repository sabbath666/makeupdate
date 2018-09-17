package com.sabbath

import org.yaml.snakeyaml.Yaml

import java.text.SimpleDateFormat

Yaml parser = new Yaml()
def sf= new SimpleDateFormat("dd.MM.yyyy HH:mm:SSS")
def config = parser.loadAs(("./upgrade.yml" as File).text, Map)
def version = config.upgrade.version
def upgradeScript = "./scr$version" as File
if (upgradeScript.isFile()) upgradeScript.delete()
upgradeScript << "# UPGRADE $version SHARE\n"
upgradeScript << "## DATE : ${sf.format(new Date())}\n"
upgradeScript <<"################ TARGET SYSTEMS ################\n"
config.upgrade.target.each{
    upgradeScript<<"#ADDRESS \$LIST_DBNAME ${it.key}  ${it.value}\n"
}
upgradeScript <<"####### ############# FILES #####################\n"

"""git diff --name-only  \$GIT_PREVIOUS_COMMIT \$GIT_COMMIT > c:/test/test.txt""".execute()