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


#usefull ressources
Console.dir($1)

#usefull ressources Online
MDN Mozilla Developer Network https://developer.mozilla.org/de/
https://www.github.com (Frank42L)


# Mein Konto Reference Seite
original auf: dece.stadt-zuerich.ch/refpage und neu auf designsystem.stadt-zuerich.ch
A) G:\Privat\Frank\PRIVAT\Fingerübungen\reference_styles\stzhrwrd-meinkonto 2018-06-22 18_53_24\prod\index_mein_konto.html
B)file:///G:/Privat/Frank/PRIVAT/Finger%C3%BCbungen/reference_styles/v7-13-3/index_mein_konto.html

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

