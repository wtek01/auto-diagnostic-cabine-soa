
### 8.1 Démarrage
1. Clonez le repository : `git clone [URL_DU_REPO]`
2. Naviguez vers le dossier du projet : `cd auto-diagnostic-cabine`
3. Construisez le projet : `mvn clean install`
4. Lancez l'application : `mvn spring-boot:run`

### 8.2 Utilisation
Une fois l'application démarrée, vous pouvez l'utiliser comme suit :

1. Ouvrez un navigateur ou utilisez un outil comme Postman.
2. Envoyez une requête GET à `http://localhost:8080/api/diagnostic/{indexSante}`, où `{indexSante}` est l'index de santé à diagnostiquer.
3. Vous recevrez une réponse JSON contenant l'index de santé et le diagnostic correspondant.

Exemple de requête : `GET http://localhost:8080/api/diagnostic/15`
Exemple de réponse :
```json
{
    "indexSante": 15,
    "diagnostic": "Cardiologie, Traumatologie"
}
