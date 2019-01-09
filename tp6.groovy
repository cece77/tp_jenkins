//
// 1 - Variable d'environnement
//
node {

  stage('0- clean'){
    deleteDir()
    checkout scm
  }

  stage ('1- Print Jenskins variables'){
    echo "$env.VAR_GLOBAL"
  }
  stage ('1- Print all env'){
    echo 'Affiche toutes les variables environnement disponibles :'
    sh 'env'
  }
}

//
// 2 - Paramètres utilisateurs du script
//

node {
  stage('2- Print parameter'){
    sh '''
       echo "Affichage du paramètre saisie par l'utilisateur"
       echo "valeur du paramètre : $missing_param"
    '''

    // Pour récupérer la valeur dans le script
    def value = params.missing_param
    def value_upper = params.missing_param.toUpperCase()

    println "Print default => " + params.missing_param
    println "Print default => " + value
    println "Print upper case value => $value_upper"

  }
}

//
// 3 - Credentials
//

node {
  stage('3- Récupération des crédentials'){
    withCredentials([
      usernamePassword(
          credentialsId: '4bda743c-53e6-4870-85c9-2d3e320263e3',
          usernameVariable: "DEMO_USERNAME",
          passwordVariable: "DEMO_PASS"
      ),
      file(
        credentialsId: '7bcfc4b3-e6a1-4558-af20-4e3dd418599b',
        variable: 'SECRET_FILE')
    ]){

      // Affichage des variables :
       echo " voici l'ID credentials $missing_credentials "
       echo " voici le username : $DEMO_USERNAME "
       echo " voici le password : $DEMO_PASS "
       echo " voici le fichier des mdp : $SECRET_FILE"
      sh 'cp $SECRET_FILE $WORKSPACE'
      sh 'ls -l'
    }
  } // end withCredential, les variables ne sont plus accessibles après
}
