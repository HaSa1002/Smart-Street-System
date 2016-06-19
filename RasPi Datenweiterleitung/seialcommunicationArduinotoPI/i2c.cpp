#include <stdio.h>
#include <string.h>
#include <curl/curl.h>
#include <iostream>
#include <unistd.h>
float letzte_geschw;

int senden(){
		CURL *curl;

    struct curl_httppost *formpost = NULL;
    struct curl_httppost *lastptr = NULL;

    curl_global_init(CURL_GLOBAL_ALL);
	int error;
    curl_formadd(&formpost,
        &lastptr,
        CURLFORM_COPYNAME, "data",
        CURLFORM_FILE, "/home/pi/Desktop/Daten.csv",
        CURLFORM_END);
    curl_formadd(&formpost,&lastptr,CURLFORM_COPYNAME, "id",CURLFORM_COPYCONTENTS,"S_01",CURLFORM_END);

    curl = curl_easy_init();

    if (curl) {
        error=curl_easy_setopt(curl, CURLOPT_URL, "http://192.168.6.102/index.php");
        printf("1");
        printf("%d",error);
        /*error=curl_easy_setopt(curl, CURLOPT_POSTFIELDS, "id=S_01");
        printf("2");
        printf("%d",error);*/
        error=curl_easy_setopt(curl, CURLOPT_HTTPPOST, formpost);
        printf("3");
        printf("%d",error);
        error=curl_easy_perform(curl);
        printf("4");
        printf("%d",error);
    }
    return 0;
}

int Datei(){
	FILE *fp;
	float akt;
	fp =fopen("/dev/ttyACM0","r");
	fscanf(fp,"%f",&akt);
	printf("gelesen: %f\n",akt);
	if(akt!=letzte_geschw){
		time_t rawtime;
		struct tm * timeinfo;
		FILE *fpout;
		time(&rawtime);
		timeinfo = localtime(&rawtime);
		fpout=fopen("/home/pi/Desktop/Daten.csv","w");
		fprintf(fpout,"%04d%02d%02d%02d%02d%02d,%0.0f",
			timeinfo->tm_year+1900,
			timeinfo->tm_mon+1,
			timeinfo->tm_mday,
			timeinfo->tm_hour,
			timeinfo->tm_min,
			timeinfo->tm_sec,akt);
		fclose(fpout);
		senden();
	}
	fclose(fp);
	return 0;
}

int main(){
	while(1){
			Datei();
			sleep(1);
		}
}
