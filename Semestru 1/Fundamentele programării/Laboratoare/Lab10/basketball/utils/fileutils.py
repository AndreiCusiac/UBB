'''
Created on 9 dec. 2020

@author: Catalin
'''
def clearFileContent(fileName):
    """
        clear content from file
        fileName - name of file to clear
    """
    f = open(fileName,"w")
    f.close()