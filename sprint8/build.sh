test=Test/WEB-INF
temporary=temporary/WEB-INF
assets=Test/Assets
cd Framework
jar cvf fw.jar *
cp fw.jar ../$test/lib/
rm fw.jar
cd ..
rm -r temporary
mkdir temporary
cd temporary
mkdir WEB-INF
mkdir Pages
mkdir Assets
cd WEB-INF
mkdir tags
mkdir jsp
mkdir classes
mkdir lib
cd ../..
cp ./$test/*.xml $temporary/
cp ./$test/tags/* $temporary/tags
cp ./$test/jsp/* $temporary/jsp
cp ./$test/lib/* $temporary/lib
cp ./$test/tags/* $temporary/tags
cp ./$test/classes/* $temporary/classes
cp -r ./$test/classes/* $temporary/classes
cp ./Test/index.jsp temporary/index.jsp
cp ./Test/Pages/* temporary/Pages
cp -r ./Test/Pages/* temporary/Pages
cp $assets/* temporary/Assets
cp -r $assets/* temporary/Assets

