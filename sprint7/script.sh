cd Test
jar -cvf Test-Framework.war *
cp Test-Framework.war /home/judi/Téléchargements/apache-tomcat-10.0.27/webapps/
rm Test-Framework.war
cd /home/judi/Téléchargements/apache-tomcat-10.0.27/webapps/
sudo ~/Téléchargements/apache-tomcat-10.0.27/bin/shutdown.sh
sudo ~/Téléchargements/apache-tomcat-10.0.27/bin/startup.sh
xdg-open http://localhost:8080/Test-Framework

# rm -r ProjetSprint
# mkdir ProjetSprint
# cp Test-Framework.war ProjetSprint
# rm Test-Framework.war
# cd ProjetSprint
# unzip Test-Framework.war
# rm Test-Framework.war