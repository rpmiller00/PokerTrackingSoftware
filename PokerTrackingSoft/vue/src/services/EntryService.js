import axios from 'axios';

export default {

  addEntry(entry) {
    return axios.post('/addEntry', entry)
  },
  getEntriesForUser(userId){
    return axios.get(`entryList/${userId}`);
  },

  deleteEntry(entryId){
    return axios.delete(`deleteEntry/${entryId}`);
  }

}