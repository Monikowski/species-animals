# species-animals
Project utilizing Docker, Java Spring Boot, HTML &amp; JavaScript.

In tab 'all species' you can add, remove or edit species, and within each species you can add, remove or edit animals. The site also serves as a file hosting -> under tab "all files" you have the option to browse and upload files.

Folder 'demo' contains the Java source code.

# running 
To run, in folder containing the docker-compose file execute:
```
docker-compose up --scale frontend=2 --scale backend=2
```
Then, go to 127.0.0.1:86 to browse the page
