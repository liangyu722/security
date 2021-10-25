./gradlew clean
rm -f android.zip
git archive HEAD -o ${PWD##*/}.zip
