import dataStore from 'nedb-promise';

export class BookRowStore {
  constructor({ filename, autoload }) {
    this.store = dataStore({ filename, autoload });
  }
  
  async find(props) {
    return this.store.find(props);
  }
  
  async findOne(props) {
    return this.store.findOne(props);
  }
  
  async insert(bookRow) {
    let bookRowTitle = bookRow.title;
    if (!bookRowTitle) {
      throw new Error('Missing title property')
    }
    let bookRowISBN = bookRow.isbn;
    if (bookRowISBN === undefined || bookRowISBN === null) {
      throw new Error('Missing isbn property')
    }
    let bookRowDate = bookRow.date;
    if (bookRowDate === undefined || bookRowDate === null) {
      throw new Error('Missing date property')
    }
    let bookRowIsReturned = bookRow.isReturned;
    if (bookRowIsReturned === undefined || bookRowIsReturned === null) {
      throw new Error('Missing is returned property')
    }
    return this.store.insert(bookRow);
  };
  
  async update(props, bookRow) {
    return this.store.update(props, bookRow);
  }
  
  async remove(props) {
    return this.store.remove(props);
  }
}

export default new BookRowStore({ filename: './db/bookRows.json', autoload: true });