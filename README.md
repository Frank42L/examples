# Umgebung starten
1) Visual Studio Code Starten
2) Browser-sync
  Command line
	G:
	Cd G:\Privat\Frank\PRIVAT\Fingerübungen\airhack_adam_bien\Frank42L-examples\first-steps\src
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
original auf: dece.stadt-zuerich.ch/refpage
A) G:\Privat\Frank\PRIVAT\Fingerübungen\reference_styles\stzhrwrd-meinkonto 2018-06-22 18_53_24\prod\index_mein_konto.html
B)file:///G:/Privat/Frank/PRIVAT/Finger%C3%BCbungen/reference_styles/v7-13-3/index_mein_konto.html

# examples
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

