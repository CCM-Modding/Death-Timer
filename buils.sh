#Buildscript made by Dries007
#Intended use is with Jenkins
VERSION="Beta0.1-${BUILD_NUMBER}"
MC="1.5.2"

FILENAME="${JOB_NAME}-Beta0.1-${BUILD_NUMBER}.jar"

echo "Building ${JOB_NAME} version ${VERSION}"
mkdir ${WORKSPACE}/output

echo "Injecting version info"
sed -i 's/@VERSION@/'${VERSION}'/g' resources/mods/deathTimer/mcmod.info

echo "Downloading N-O..."
wget https://github.com/CCM-Modding/Nucleum-Omnium/archive/master.zip
unzip master.zip
echo "Downloading Forge..."
wget http://files.minecraftforge.net/minecraftforge/minecraftforge-src-latest.zip
unzip minecraftforge-src-*.zip
rm minecraftforge-src-*.zip
cd forge
echo "Installing Forge..."
bash ./install.sh
cd mcp

cd src
echo "Copying ${JOB_NAME} into MCP..."
cp -rf ${WORKSPACE}/common/* ./minecraft/
echo "Copying ${JOB_NAME}'s libs into MCP..."
cp -rf ${WORKSPACE}/Library/* ./minecraft/
cd ..

echo "Recompiling..."
bash ./recompile.sh

echo "Reobfuscating..."
bash ./reobfuscate_srg.sh

echo "Creating mod package"
cd reobf/minecraft/
mkdir -p resources/mods/deathTimer
cd resources/mods/deathTimer
cp -rf ${WORKSPACE}/resources/mods/deathTimer/* .
cd ../../..
cp -rf ${WORKSPACE}/resources/mods/deathTimer/mcmod.info .

jar cvf "${WORKSPACE}/output/${FILENAME}" ./ccm/deathtimer/* ./resources/mods/deathTimer/* mcmod.info

echo "Done!"