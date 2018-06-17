# examples
Eigene Beispiele auf Github
- erster Schritt - Gehversuche mit Git
- zweiter Schritt - Gehversuch mit Pull Requests (firstpull)
- dritter Schritt - Gehversuch mit Pull Request (firstpull zum zweiten)
- vierter Schritt - Gehversuch mit Pull Request (secondpull)

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

