import { createStore as _createStore } from 'vuex';
import axios from 'axios';
import EntryService from '../services/EntryService.js';


export function createStore(currentToken, currentUser) {
  let store = _createStore({
    state: {
      token: currentToken || '',
      user: currentUser || {},
      entryList: [],
      entry: {
        userId: 0,
        amount: 0,
        gameSize: "",
        gameType: "",
        location: "",
        startTime: "",
        endTime: "",
        hours: 0

      },
    },
    mutations: {
      SET_AUTH_TOKEN(state, token) {
        state.token = token;
        localStorage.setItem('token', token);
        axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
      },
      SET_USER(state, user) {
        state.user = user;
        localStorage.setItem('user', JSON.stringify(user));
      },
      LOGOUT(state) {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        state.token = '';
        state.user = {};
        state.entryList = [];
        axios.defaults.headers.common = {};
      },
      async UPDATE_ENTRIES(state){
        try{
          const output = await EntryService.getEntriesForUser(state.user.id);
          state.entryList = output.data;
        } catch (error){
          console.log(error);
        }


        // EntryService.getEntriesForUser(state.user.id)
        // .then((response) =>{
        //   state.entryList = response.data;
        // });
        },
        
      }


    },
  );
  return store;
}
