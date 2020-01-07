import os

fileName = "SQA - Sheet1.csv"

fileString = open(fileName).read()


lines = fileString.split("\n")
lines.pop(0)
command = ""
def writeOutput(command, fileName, content):
    directoryString = "output/"+command+"/"
    directory = os.path.dirname(directoryString)
    if(not os.path.exists(directory)):
        os.makedirs(directory)
    file = open(directoryString+fileName+".out", "w")
    file.write(content+'\n')
    file.close()

def writeInput(command, fileName, content):
    directoryString = "input/"+command+"/"
    directory = os.path.dirname(directoryString)
    if(not os.path.exists(directory)):
        os.makedirs(directory)
    splitContent = content.split(" ")
    if "" in splitContent or '' in splitContent:
        print(splitContent)
    fileContent = "\n".join(splitContent)
    file = open(directoryString+fileName+".in", "w")
    file.write(fileContent)
    file.close()

for line in lines:
    parsedLine = line.split(",")
    if(parsedLine[0] != ""):
        command = parsedLine[0]
    writeOutput(command, parsedLine[1] +"_"+command, parsedLine[5])
    writeInput(command, parsedLine[1] +"_"+command, parsedLine[4])
