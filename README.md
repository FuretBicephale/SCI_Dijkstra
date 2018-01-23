# SCI_Dijkstra
##### Nicolas CACHERA et Samuel GRANDSIR

### Dijkstra

Cette application a pour but de simuler le comportement de deux types d'agents : Les attracteurs et les chasseurs.

Tout comme les deux précédentes applications, celle-ci fait évoluer les agents dans un environnement 2D discret torique ou non et les agents se déplacent selon un voisinage de Moore d'ordre 1 et prennent une décision par tour.

Les attracteurs ont pour objectif de fuir les chasseurs qui sont attirés par ces premiers et ont pur but de les tuer. Pour ce faire, ces agents utilisent l'algorithme de Dijkstra pour trouver le plus court chemin afin de se rapprocher de sa cible dans le cas d'un chasseur ou de s'éloigner de ses poursuivants dans le cas d'un attracteur. Les attracteurs sont représentés en jaune et les chasseurs en vert.

L'algorithme de Dijkstra est calculé dans la classe statique "DijkstraComputer" et retourne un tableau 2D, représentant l'environnement, avec une valeur par case. Ces valeurs correspondent à la distance entre la case et la cible où à une case inaccessible si la valeur est -1. Chaque agent possède son tableau de Dijkstra mis à jour chaque tour et pouvant être récupéré par n'importe quel autre agent. Ainsi, les chasseurs vont récupérer les tableaux de Dijkstra de tous les attracteurs, afin de se rapprocher du plus proche et les attracteurs vont récupérer les tableaux de Dijkstra de tous les chasseurs afin de s'eloigner du plus proche.

Enfin, les agents de type répulseurs, représentés en rouge, ont été ajoutés. Ces agents se déplacent de façon aléatoire et vont repousser les chasseurs sans repousser les attracteurs. Pour ce faire, les attracteurs vont, après avoir mis à jour leur tableau de Dijkstra, donner une forte valeur au voisinage de Moore d'ordre REPULSER_RANGE du répulseur afin d'empêcher les chasseurs d'aller sur ces cases. Cette valeur à été calculée de sorte à être forcement plus forte que n'importe quelle autre valeur du tableau et décroit du centre vers l'extérieur du voisinage du répulseur afin d'éviter d'y coincer les chasseurs.

##### Pour compiler (dossier src) :
* javac ./\*/\*.java

##### Pour créer le jar (dossier src) :
* jar cvfe Dijkstra.jar dijkstra.Simulation ./\*/\*.class

##### Pour exécuter (dossier src) :
* java -jar Dijkstra.jar \<nb\_cases\_largueur\> \<nb\_cases\_hauteur\> \<taille\_cases\> \<toric (true/false)\> \<latence en ms\> \<nb\_attracteurs\> \<nb\_chasseurs\> \<nb\_repulseurs\> \<nb\_walls\> [\<nb\_tours\>]

##### Exemple :
* java -jar Dijkstra.jar 50 50 10 true 100 200 50 5 200 : Environnement torique de 50 sur 50 cases de 10 pixels de côté contenant 200 attracteurs, 50 chasseurs, 5 répulseurs et 200 murs. L'environnement fait 500 pixels sur 500 pixels. La simulation dure indéfiniment et attends 100 ms entre chaque tour.

