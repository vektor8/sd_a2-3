import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import { BrowserRouter } from 'react-router-dom';
import App from './App';
import { PersistGate } from 'redux-persist/integration/react';
import './index.css';
import reportWebVitals from './reportWebVitals';
import { persistor, store } from './stores/store';
import { customAxios } from './api/api';
import { login, logout } from './stores/user/slice';

ReactDOM.render(
  <Provider store={store}>
    <PersistGate persistor={persistor}>
      <BrowserRouter>
        <App />
      </BrowserRouter>
    </PersistGate>
  </Provider>,
  document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();

const { dispatch } = store;
customAxios.interceptors.response.use((response) => {
  return response
}, async function (error) {
  const originalRequest = error.config;
  if (error.response.status === 403 && !originalRequest._retry) {
    originalRequest._retry = true;

    const refresh_token = localStorage.getItem("refresh_token") ?? "";
    localStorage.setItem("access_token", refresh_token);

    customAxios.get("/api/user/token").then(res => {
      localStorage.setItem("access_token", res.data.access_token);
      localStorage.setItem("refresh_token", res.data.refresh_token);
    }).catch(err => {
      localStorage.removeItem("access_token");
      localStorage.removeItem("refresh_token");
      dispatch(logout());
    });

    customAxios.defaults.headers.common['Authorization'] = 'Bearer ' + localStorage.getItem("access_token");
    return customAxios(originalRequest);
  }
  return Promise.reject(error);
});
