## IoT device simulator

Publishing the statistic data for next stub devices

1. energy device
2. power device
3. temperature device 

to MQTT broker topics : energy_topic, power_topic, temperature_topic
#####Data format
Energy model:

````
{
	"id" : "EnergyDevice"
	"energy": 12.00
	"timestamp": "2018-05-17T13:58:21.004"
}
````
Power model:

````
{
	"id" : "EnergyDevice"
	"power": 12.00
	"timestamp": "2018-05-17T13:58:21.004"
}
````
Temperature model:

````
{
	"id" : "EnergyDevice"
	"temperature": 12.00
	"timestamp": "2018-05-17T13:58:21.004"
}
````
#####Prerequisites
You will need to install next software, before you'll run app: 

1. java v1.8
2. git v2.15.1
3. maven v3.5.2
4. docker v18.03.1 
5. docker-compose v1.21.1

Please see bellow, the installation example for Linux OS 

```
//install java
sudo add-apt-repository ppa:webupd8team/java
sudo apt update
sudo apt install oracle-java8-installer
javac -version
//install maven
sudo apt-get install maven
//install git
apt-get install git
//install docker
sudo apt-get install docker-ce

```
#####Installing
Before you will use the app you will need to execute next commands:

```
git clone ${repo_url}
cd {project_path}
mvn clean install
docker-compose build
docker-compose up

```

