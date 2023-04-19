javac -d . *java
chmod 777 annotation
chmod 777 etu1968
cp -r annotation Framework
cp -r etu1968 Framework
cd 'Framework/etu1968/framework/servlet'
find . -maxdepth 1 ! -name "FrontServlet.class" ! -name "Mapping.class" ! -name "ModelView.class" -type f -delete
cd './../../../../'
cp -r etu1968 Test/WEB-INF/classes
cd 'Test/WEB-INF/classes/etu1968/framework/servlet/'
rm FrontServlet.class 
rm Mapping.class
cd './../../../../../../'
rm -r annotation
rm -r etu1968