class MyHashTable:

    def __init__(self, m):
        self.__m = m
        self.__list = [[] for _ in range(m)]
        #self.__no_of_elements = 0

    def hashCode(self, atom):
        ascii_sum = 0
        # print(atom)

        for letter in atom:
            ascii_sum += ord(letter)

        # print("Ascii sum:", ascii_sum)
        return ascii_sum

    def hashFunction(self, atom):
        # print("Value of hashF:",self.hashCode(atom) % self.__m)
        return self.hashCode(atom) % self.__m

    def addElement(self, atom):
        #new_tuple = (atom, self.__no_of_elements)
        # print(new_tuple)
        # print(self.__no_of_elements)
        # print(self.__list)
        self.__list[self.hashFunction(atom)].append(atom)
        # print(self.__list)
        #self.__no_of_elements += 1
        # print(self.__no_of_elements)


    def findElement(self, atom):
        li = self.__list[self.hashFunction(atom)]

        for el in li:
            if el == atom:
                return self.hashFunction(atom) * 10 + self.__list[self.hashFunction(atom)].index(el)

        return -1

    def getAll(self):

        all = []

        for el in self.__list:
            for i in el:
                all.append(i)

        return all

    def getTS(self):
        l = []

        for el in self.getAll():
            new_tuple = (el, self.hashFunction(el) * 10 + self.__list[self.hashFunction(el)].index(el))
            l.append(new_tuple)

        return l

    def getInternalForm(self):
        return self.__list