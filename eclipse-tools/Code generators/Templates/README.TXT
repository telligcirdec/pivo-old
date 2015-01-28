															BUT: 
															
Tenter d'accélerer les développements en fournissant des raccourcis permettant de générer du code en tapant le début 
d'un mot clé suivi de ctrl+espace (un peu comme syso + ctrl + espace pour faire un System.out.println (...))



														INSTALLATION :
														
- importer les templates Java (templates_java.xml) via Window / Preferences / Java / Editor / Templates / Import... 
- importer les templates Maven (templates_maven.xml) Window / Preferences / Maven / Templates / Import..
Ils contiennent un nom de raccourcis et une description (qui commence toujours pas custom_ ) 



												RACCOURCIS JAVA DISPONIBLES :

ccc + ctrl + espace  (pour custom comment code, ne fonctionne qu'à l'intérieur d'une méthode) :

/* ================== *
 *                      
 * ================== */

										~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

ccm + ctrl + espace :

    /* ======================================================= *
     *                      code métier 
     * ======================================================= */


										~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

cco + ctrl + espace :

    /* ======================================================= *
     *                      constructeurs
     * ======================================================= */


										~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

cgs + ctrl + espace (pour comment getter setter) :

    /* ======================================================= *
     *                      getters & setters 
     * ======================================================= */


										~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

cinherit + ctrl + espace  (héritage d'un commentaire de la méthode de la classe mère, pour que Sonar les interprète) :

/** {@inheritDoc} */
 

										~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


logger + ctrl + espace (ne fonctionne qu'en tant que variable globale) :
final Logger LOGGER = LoggerFactory.getLogger(getClass());


										~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

cequalshash  :

    /* ======================================================= *
     *                      equals / hash code
     * ======================================================= */

										~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

cjsfvalidation  :

FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, ${cursor}, null);
										

												RACCOURCIS MAVEN DISPONIBLES :

Côté maven, pour générer une nouvelle dépendance, il faut aller dans le bloc <dependencies> et faire ctrl + espace et choisir 
custom_new_dependency dans le menu déroulant. Ca affichera : 

		<dependency>
			<groupId>groupId</groupId>   
			<artifactId>artifactId</artifactId>
			<version>version</version>
		</dependency>

Avec le curseur positionné à gauche de "groupId"
