# Umgebung starten
1) Visual Studio Code Starten
2) Browser-sync
  Command line
	G:
	Cd G:\Privat\Frank\PRIVAT\Fingerübungen\airhack_adam_bien\Frank42L-examples\first-steps\src
  Cd G:\Privat\Frank\PRIVAT\Fingerübungen\airhack_adam_bien\Frank42L-examples\geburtstagsliste\src
	Browser-sync start --server --files "*"
3) Die URL localhost:3000 im Chrome starten (da im IE ohne Massanhem nicht alles unterstützt ist)
4) msys2 (Bash Shell) - Desktop "Git Bash" oder Mintty in Startleiste
  (Program befindet sich hier: C:\Users\Frank\AppData\Local\Programs\Git\usr\bin\mintty)
  cd /G/Privat/Frank/PRIVAT/Fingerübungen/airhack_adam_bien/Frank42L-examples

# Serverseitig 
5) http://tomcat:8080/BirthdayList/birthdays/user/frank.loeliger 
6) powershell und SSH zu Development server (keypass)
   (SSH {USERNAME}@192.168.1.119{ENTER}{DELAY 2000}{PASSWORD}{ENTER})
7) sudo docker exec -it tomcat1 bash
8) cd logs
9) tail -f catalina.yyyy-mm-dd.log
10) Verzeichnis  /tomcat  (Docker) -> /volume1/webservices2/tomcat (NAS)
11) Export WAR FEil (Jerseydemo) to C:\temp
12) tomcat:8080/manager/html INstallieren WAR Datei


# usefull ressources
Console.dir($1)

#usefull ressources Online
MDN Mozilla Developer Network https://developer.mozilla.org/de/
https://www.github.com (Frank42L)


# Entwicklungsumgebung
eclipse starten

# junit/Maven Helper
https://maven.apache.org/guides/getting-started/index.html#How_do_I_setup_Maven
- https://repo.maven.apache.org/maven2/.
- /maven2/log4j/log4j.

# Tomcat (Falls keine Entwicklung)
Start mit
  %CATALINA_HOME%\bin\startup
  http:\\localhost:8080\helloworld

# REST Services after Tomcat runs (see ecplise)
Gute Erklärungen zum Aufbau von REST APIS
- https://jax.de/blog/software-architecture-design/restful-apis-richtig-gemacht/
- https://jsonapi.org/format/ (Noch nicht von diesem Beispiel so umgesetzt)

http://localhost:8080/jerseydemo/sayhello/frank
http://localhost:8080/jerseydemo/birthdays/user/frank.loeliger
http://localhost:8080/jerseydemo/birthdays/user/irene.troxler
http://localhost:8080/jerseydemo/birthdays/user/frank.loeliger/Loeliger/Frank
http://localhost:8080/jerseydemo/birthdays/user/frank.loeliger/Muster/Hans
http://localhost:8080/jerseydemo/birthdays/users/Muster/Hans?user=frank.loeliger&user=irene.troxler

http://localhost:8080/jerseydemo/emailverification/frank.loeliger@bluewin.ch
http://localhost:8080/jerseydemo/emailsending/mail@frank.loeliger.name

Ersatz der aller Geburtstage: POST http://localhost:8080/jerseydemo/birthdays/user/frank.loeliger.test mit JSON File
Hinzufügen falls noch nicht drin: PATCH http://localhost:8080/jerseydemo/birthdays/user/frank.loeliger.test mit JSON File

  
# examples GIT
Eigene Beispiele auf Github
- erster Schritt - Gehversuche mit Git
- zweiter Schritt - Gehversuch mit Pull Requests (firstpull)
- dritter Schritt - Gehversuch mit Pull Request (firstpull zum zweiten)
- vierter Schritt - Gehversuch mit Pull Request (secondpull)

Git Start:
- Starte Gitbasch
- Startverzeichnis: /G/Privat/Frank/PRIVAT/Fingerübungen/airhack_adam_bien/Frank42L-examples
- git diff 

Ablauf:
- git checkout -b secondpull
- git add --all
- git commit -m "vierter Schritt mit secondpull"
- git push origin secondpull
  eigentlich "git push origin secondpull:secondpull

  oder 
  - git push --set-upstream origin secondpull
  - git push -u origin secondpull

  oder noch besser: 
  - Set push.default = current, dann geht git push -u

-----
-----
Aktuell empfohlener Ablauf:
- git checkout -b XXXX
- ... edit ...
- git add --all
- git commit -m "blabla"
- git push -u origin XXXX 
  (beim zweiten mal reicht git push)
-----
- git fetch origin
- git checkout -b XXXX origin/XXXX
  (falls derselbe Mitarbeiter reicht: git checkout XXXX)
- merge master
----
- git checkout master
- git merge --no-ff XXXX
- git push origin master
-----
- git push origin --delete XXXX


UM MIT BRANCHES Arbeiten zu können:
- git branch new-feature (falls branch erwünscht)
- git checkout new-feature master
- git commit -m "First work on new-feature done, will work tomorrow on next part of feature"
- git add <file>
- git commit -m "finished work on feature"
- ...
- git checkout master (Status bevor wir mit feature begonnen haben)
- git merge new-feature (merged den  master mit dem aktuellen Branch "new-feature" und erzeugt einen merge-commit)
- git branch -d new-feature

 --> Gute Erklärung unter https://blog.seibert-media.net/blog/2015/08/05/git-mit-branches-arbeiten-git-merge/
    - Fast-forward (rebasing) -> Niemand sonst hat gebranchet
    - 3-way-merge -> falls es einen anderen Branche gibt.


  Docker Image - Allgemeiner Ablauf
  - Build Docker Image
    docker build -t [Name Of the Image]  .
    docker build -t [Name Of the Image]  [Docker hub name]
  - Running the Docker Image
    docker container run -it -d --name [container name] -p port:port [image name]
  - Erklärungslinks
    https://cloudinfrastructureservices.co.uk/how-to-create-a-tomcat-docker-container-docker-tomcat-image/
  - Einrichten Tomcat manager Console
    https://stackoverflow.com/questions/42692206/docker-tomcat-users-configuration-not-working

  Docker umgebung, Spezifischer Ablauf
  - Docker Container auf DS220+ mit Laufwerk /webservices 
    Infos dazu auch unter: G:\Privat\Frank\PRIVAT\Fingerübungen\Infrastruktur\DS220\FreigegebeneLaufwerke
    see also: https://www.synology.com/de-de/dsm/packages/Docker
  - Installation Docker
  - Testing Installation of Docker
      sudo docker run hello-world
      resp: aus der Dockerumgebung vom DS220 starten von Hand
      evtl nötig:
        sudo systemctl start docker
        oder 
        sudo service docker start

 
  Docker Einrichten Tomcat
  - DS220/Pkg Docker 
    -> Registrierung von tomcat:9.0.54-jdk16
    -> Container erstellen tomcat1 (/usr/local/tomcat)
      -> Details (Terminal), um "reinzuschauen" oder
      -> sudo docker exec -it tomcat1 bash 
  - Einrichten Manager Console Tomcat (Manuell)
      - cd /volume1/webservices2/tomcat
      - sudo docker cp tomcat1:/usr/local/tomcat/conf/tomcat-users.xml .
      - vi tomcat-users.xml (add user tomcatadmin and tomcatdeploy))
          <tomcat-users xmlns="http://tomcat.apache.org/xml"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xsi:schemaLocation="http://tomcat.apache.org/xml tomcat-users.xsd"
              version="1.0">
          <role rolename="manager-gui"/>
          <role rolename="manager-script"/>
          <role rolename="admin-gui"/>
          <role rolename="admin-script"/>
          <user username="tomcatadmin" password="<must be changed>" roles="manager-gui, manager-script"/>
          <user username="tomcatdeploy" password="<must be changed>" roles="manager-script"/>
          <user username="tomcathostmanagergui" password="<must be changed>" roles="admin-gui"/>
          <user username="tomcathostmanagerscript" password="<must be changed>" roles="admin-script"/>
      - sudo docker cp ./tomcat-users.xml tomcat1:/usr/local/tomcat/conf
      - sudo docker exec -it tomcat1 bash
          cd /usr/local/tomcat/conf
          chown root:root tomcat-users.xml
          chmod 644 tomcat-users.xml
          catalina.sh stop    [stopped aber auch gleich den Container, der dann automatisch neu gestartet wird]


      - sudo docker cp tomcat1:/usr/local/tomcat/webapps/manager/META-INF/context.xml .
      - vi context.html (portforwarding)
           <Context antiResourceLocking="false" privileged="true" >
            <!--
              <Valve className="org.apache.catalina.valves.RemoteAddrValve"
                  allow="127\.\d+\.\d+\.\d+|::1|0:0:0:0:0:0:0:1" />
            -->
            <Manager sessionAttributeValueClassNameFilter="java\.lang\.(?:Boolean|Integer|Long|Number|String)|org\.apache\.catalina\.filters\.CsrfPreventionFilter\$LruCache(?:\$1)?|java\.util\.(?:Linked)?HashMap"/>     
          </Context>
      - sudo docker cp context.xml tomcat1:/usr/local/tomcat/webapps/manager/META-INF/
      - sudo docker exec -it tomcat1 bash
          cd webapps/manager/META-INF/
          chown root:root context.xml
          chmod 644 context.xml
          catalina.sh stop    [stopped aber auch gleich den Container, der dann automatisch neu gestartet wird]

      - sudo docker cp tomcat1:/usr/local/tomcat/conf/Catalina/localhost/host-manager.xml .
      - vi host-manager.xml 
          <Context privileged="true" antiResourceLocking="false" 
              docBase="{catalina.home}/webapps/host-manager">
              <Valve className="org.apache.catalina.valves.RemoteAddrValve" allow="^.*$" />
          </Context>
      - sudo docker cp host-manager.xml tomcat1:/usr/local/tomcat/conf/Catalina/localhost/
      - sudo docker exec -it tomcat1 bash
          cd /usr/local/tomcat/conf/Catalina/localhost/
          chown root:root host-manager.xml
          chmod 644 host-manager.xml
          catalina.sh stop    [stopped aber auch gleich den Container, der dann automatisch neu gestartet wird]

    - Servlet "BirthdayList" gegen aussen sichtbar machen
      - cd /volume1/webservices2/tomcat
      - sudo docker cp tomcat1:/usr/local/tomcat/webapps/BirthdayList/META-INF/BirthdayList.xml  .
      - vi BirthdayList.xml (portforwarding)
          <Context privileged="true" antiResourceLocking="false" 
              docBase="{catalina.home}/webapps/BirthdayList">
              <Valve className="org.apache.catalina.valves.RemoteAddrValve" allow="^.*$" />
          </Context>
      - sudo docker cp BirthdayList.xml tomcat1:/usr/local/tomcat/webapps/BirthdayList/META-INF/
      - sudo docker exec -it tomcat1 bash
          cd webapps/BirthdayList/META-INF/
          chown root:root BirthdayList.xml
          chmod 644 BirthdayList.xml
          catalina.sh stop    [stopped aber auch gleich den Container, der dann automatisch neu gestartet wird]

    - Expose the Admin GUI to the host (DS220)
        How to: https://octopus.com/blog/deployable-tomcat-docker-containers
        - Portforwarding: https://pythonspeed.com/articles/docker-connection-refused/ 
        - Load manager applications (Move directory /user/local/tomcat/webapps.dist to /usr/local/tomcat/webapps). This is achieved by overriding the command used when launching the container ()
        Variante A: When Docker container is being started
                  sudo docker run \
                    --name tomcat \
                    -it \
                    -p 8080:8080 \
                    -v /tmp/tomcat-users.xml:/usr/local/tomcat/conf/tomcat-users.xml \
                    -v /tmp/context.xml:/tmp/context.xml \
                    tomcat:9.0 \
                    /bin/bash -c "mv /usr/local/tomcat/webapps /usr/local/tomcat/webapps2; mv /usr/local/tomcat/webapps.dist /usr/local/tomcat/webapps; cp /tmp/context.xml /usr/local/tomcat/webapps/manager/META-INF/context.xml; catalina.sh run"
        Variante B: Configure in DS220+ (not clear how to change the standard command in DS220+ yet)
                    Or replace "catalina.sh run" by "mv /usr/local/tomcat/webapps /usr/local/tomcat/webapps2; mv /usr/local/tomcat/webapps.dist /usr/local/tomcat/webapps; cp /tmp/context.xml /usr/local/tomcat/w
                    webapps/manager/META-INF/context.xml; catalina.sh run"
        Variante C (aktuell): Nach Aufsetzen des Dockerimages manuell (falls noch nicht dort)
            sudo docker exec -it tomcat1 bash
                mv /usr/local/tomcat/webapps /usr/local/tomcat/webapps2
                mv /usr/local/tomcat/webapps.dist /usr/local/tomcat/webapps
                cp /tmp/context.xml /usr/local/tomcat/webapps/manager/META-INF/context.xml
                catalina.sh stop
                catalina.sh start

          

  - Nutzen der Admin Consolen von tomcat
    - http://tomcat:8080                      [tomcat test page]
    - http://tomcat:8080/manager/html         [tomcat manager webapp]
    - http://tomcat:8080/host-manager/html    [tomcat host-manager webapp]
  - Nutzen des TEXT APIS der Management Console
    - https://www.baeldung.com/tomcat-manager-app
    

  - nützliche Commandline Befehle (von einer SSH Shell aus)
    - sudo docker ps
    - sudo docker restart tomcat1
    - sudo docker cp tomcat1:src_path dest_path
    - sudo docker cp src_path tomcat1:dest_path
    - sudo docker exec -it tomcat1 bash         [bash shell innerhalb des dockerimages tomcat1]


  Docker Image for Bortihdaylist (DRAFT)
  - docker build -t frankl42/birthday_service  .
  - docker container run -it -d --name birthday_service_001 -p 8080:8080 frankl42/birthday_service
  - Stichworte zum Dockerimage (Welche Versionen?)
    - docker pull tomcat:9.0.54-jdk16


  Docker Important Commands
    docker ps – Lists all running containers
    docker ps all – Lists all docker containers, including stopped ones
    docker images – Lists all the docker images
    docker search [image-name] – searches for the images in the docker hub
    docker kill [containerid] – kills the container.
    docker stop [containerid] – pauses the container.
    docker restart [containerid] – Restarts the stopped container
    docker push [image name] – Pushes an image from an environment to the docker hub
    docker user Guide: https://docs.docker.com/engine/
    docker ref documentation: https://docs.docker.com/reference/


Commandline und einige oft gebrauchte unix befehle (neben den üblilchen Standard Befehlen)
    ssh user@ip 
    find . -type f | wc -l              [anzahl files in subdirectory]
    du -ah /path/dir                    [size of a subdirectory]
    find / -name filename               [find a file]
    cp -r -p /srcDir /destPath          [copy directory and subdirectories]
    tar -cvf archfilename.tar  *        [create uncompressed tar]
    tar -cvzf archfilename.tgz *        [create compressed tar]
    tar xvf file.tar                    [extract uncompressed  tar]
    tar xvzf file.tar.gz                [extract compressed  tar]
    reboot                              [reboot]
    poweroff                            [shutdown]
    kill processID                      [stop process]
    killall programName                 [stop a program]
    top                                 [display most current processes running]
    df                                  [disk space usage]
    ps                                  [all running processes]
    netstat -an                         [show currently open ports and their status]
    su - xxx                            [open a new shell as user xxx]
    ifconfig                            [network interfaces]

    DS Specific / ubuntu commands
      ipkg install filename               [install a pkg]
      ipkg remove filename                [uninstall a pkg]
      /usr/syno/etc/rc.d/S97apache-user.sh restart      [restart apache]
      /usr/syno/etc/rc.d/S81atalk.sh restart            [restart appletalk]
      /usr/syno/etc/rc.d/S04crond.sh stop               [stop crond]
      /usr/syno/etc/rc.d/S04crond.sh start              [start crond]
      systemctl restart crond                           [restart crond]
      systemctl restart synoscheduler                   [restart synoscheduler]
      /usr/syno/etc/rc.d/S99ftpd.sh restart             [restart ftp]
      /usr/syno/etc/rc.d/S21mysql.sh restart            [restart mysql]
      /usr/syno/etc/rc.d/S83nfsd.sh restart             [restart nfs]
      /usr/syno/etc/rc.d/S20pgsql.sh restart            [restart postgresql]
      /usr/syno/etc/rc.d/S80samba.sh restart            [restart samba]
      /usr/syno/bin/synosystemctl restart sshd.service  [restart sshd]
      /usr/syno/bin/synosystemctl disable sshd.service  [disable sshd]
      /usr/syno/bin/synosystemctl enable  sshd.service  [enable sshd]
      /usr/syno/bin/synosystemctl start   sshd.service  [start sshd]
      /usr/syno/bin/synosystemctl status tomcat         [status tomcat]
      cat /proc/meminfo                                 [check system memory info]
      cat /proc/cpuinfo                                 [check CPU Info]
      cat /proc/interrupts                              [check Interrupts in use]
      cat /proc/filesystems                             [check current filesystem in use]
      tune2fs -l /dev/hda3                              [comprehensive information on the file system format]
      cat /proc/version                                 [check Linux version]
      cat /proc/mdstat                                  [check RAID Devices]
      env                                               [check environment variables]
      cat /proc/diskstats                               [check physical and logical disk/partitions in a multibay NAS]
      cat /proc/partitions                              [check dito for all nas types]
      fdisk -l                                          [similar with other info]
      clear                                             [clear terminal screen]
    
Deploy and run under docker
  Deploy Manually with tomcat manager GUI
    - tomcat:8080/manager/html
    - Installieren - 
        Kontextpfad                = 
        Version                    = --
        XML Konfigrationsdatei URL = 
        WAR oder Verzeichnis URL   = 
    - INstallieren "*.war uploaden                                  [deploy]
  - http://tomcat:8080/BirthdayList/birthdays/user/frank.loeliger   [Aufruf]

# Docker Konfiguration
#   Volume-Einstellungen
/webservices2/tomcat          gemounted auf /tomcat
/webservices2/data_serverside gemounted auf /data_serverside

TODO:
- host_manager wieder deaktivieren!