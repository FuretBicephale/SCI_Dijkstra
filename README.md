# SCI_Wator
##### Nicolas CACHERA et Samuel GRANDSIR

### Billes

Cette application a pour but de simuler le comportement de particules (ou billes) dans un environnement 2D discret.

Les billes sont des agents qui se déplacent dans huit directions. Pour ce faire, nous avons donné deux vitesses à chaque bille : une en X et une en Y. La valeur de ces vitesses (entre -1 et 1) correspond au nombre de cases dont la bille va se déplacer en un tour dans l'axe correspondant. -1 correspond donc à une case vers la gauche (X) ou le haut (Y), 0 à aucun deplacement et 1 à une case vers la droite (X) ou le bas (Y). 

L'environnement est un tableau 2D discret ne pouvant pas contenir plus d'un agent dans une case. Ce tableau peut être torique ou non en fonction du choix de l'utilisateur.

A l'initialisation, toutes les billes sont ajoutées à l'environnement dans une case aléatoire non occupée. Elles ont aussi leurs vitesses attribuées aléatoirement. A chaque tour, la liste des agents est mélangée et chaque agent prendra une décision et effectuera une action chacun leur tour. Toutes les billes vont donc effectuer le même nombre d'action dans une simulation.

Lors de la prise de décision, l'agent va regarder la case où il est censé aller :
* Si cette case est vide il y va. 
* Si elle est hors-limite et que l'environnement est torique, l'agent se retrouve du côté opposé de l'environnement 
* Si elle est hors-limite et que l'environnement est non torique, l'agent rebondit contre un mur en opposant sa vitesse en X si le mur est vertical, en Y si il est horizontal, dans les deux si c'est un coin.
* Si la case contient un autre agent, il va prévenir cet agent de la collision puis, une fois que cet agent aura pris en compte cette collision, opposera sa vitesse en X et en Y. Il ne bouge pas ce tour.

Lorsqu'un agent est prévenu d'une collision provoquée par un autre agent, il la prend en compte en prenant la vitesse en X et en Y de cet agent, peut importe ses vitesses initiales.

##### Pour compiler (dossier src) :
* javac ./\*/\*.java

##### Pour créer le jar (dossier src) :
* jar cvfe Billes.jar billes.Simulation ./\*/\*.class

##### Pour exécuter (dossier src) :
* java -jar Billes.jar \<nb\_cases\_largueur\> \<nb\_cases\_hauteur\> \<taille\_billes\> \<toric (true/false)\> \<latence en ms\> \<nb\_billes\> [\<nb\_tours\>]

##### Exemple :
* java -jar Billes.jar 50 50 10 false 1000 20 100 : Environnement non torique de 50 sur 50 cases de 10 pixels de côté contenant 20 billes. L'environnement fait 500 pixels sur 500 pixels. La simulation dure 100 tours et attends 1000 ms entre chaque tour.
* java -jar Billes.jar 50 50 10 false 1000 20 : Environnement non torique de 50 sur 50 cases de 10 pixels de côté contenant 20 billes. L'environnement fait 500 pixels sur 500 pixels. La simulation ne s'arrête jamais et attends 1000 ms entre chaque tour.

### Wator

Cette application a pour but de simuler un environnement 2D discret constitué de poissons (thons et requins).

Les poissons sont des agents qui se déplacent dans huit directions, de la même façon que les billes à la différence que les poissons choisissent, à chaque tour, la case où ils se déplacent. En effet, à chaque tour, le poisson analyse son environnement (Son voisinage de Moore d'ordre 1) et repère les cases vides. Il se déplacera alors vers une de ses cases, choisie aléatoirement.

Les poissons peuvent aussi se reproduire. En effet, ils possèdent une fréquence de reproduction de façon à ce qu'ils donnent naissance tous les X tours si il y a au moins une case vide autour d'eux. A chaque début de tour, si les conditions sont réunies (Au moins X tours depuis la dernière naissance et au moins une case vide dans son voisinage), le poisson créera un autre poisson de la même espece dans une case voisine vide choisie aléatoirement parmis les cases vides avant de se déplacer. Sinon, il se déplace sans donner naissance à un poisson et retentera le tour suivant.

Les requins sont des poissons ayant la particularité de manger des poissons de type thon. Si un requin ne mange pas pendant un nombre pré-défini de tours, il meurt. Il est donc nécessaire que chaque requin mange dès que possible. Pour ce faire, le requin va analyser son environnement pour trouver les thons voisins. Si il en trouve, il mange un de ces poissons, choisi aléatoirement, et se déplace sur sa case. Sinon, il se déplace et retentera la prochaine fois ou meurt si il n'a pas mangé depuis trop longtemps.

Dans la vue, les thons sont représentés par des cercles gris et les requins par des cercles bleus.

A la fin de la simulation, deux graphes sont générés. Le premier (population), constitué de deux courbes, donne le nombre de thons et de requins en fonction du tour. Le second (ratio) donne le ratio entre les thons et les requins. Afin d'afficher ces graphes, il est possible d'utiliser les deux lignes de commandes suivantes dans le dossier SCI_WATOR :
* gnuplot population
* gnuplot ratio

##### Pour compiler (dossier src) :
* javac ./\*/\*.java

##### Pour créer le jar (dossier src) :
* jar cvfe Wator.jar wator.Simulation ./\*/\*.class

##### Pour exécuter (dossier src) :
* java -jar Wator.jar \<nb\_cases\_largueur\> \<nb\_cases\_hauteur\> \<taille\_cases\> \<toric (true/false)\> \<latence en ms\> \<nb\_thons\> \<nb\_requins\> [\<nb\_tours\>]

##### Exemple :
* java -jar Wator.jar 50 50 10 true 100 500 100 : Environnement torique de 50 sur 50 cases de 10 pixels de côté contenant 500 thons et 100 requins. L'environnement fait 500 pixels sur 500 pixels. La simulation dure indéfiniment et attends 100 ms entre chaque tour.
