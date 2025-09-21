import ReactDOM from 'react-dom/client';
import App from './App';
import { auth, loadAndSetAuth } from './auth-context/AuthProvider';
import './index.css';
import reportWebVitals from './reportWebVitals';

await loadAndSetAuth();

console.log("index.ts 1", auth);

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);

root.render(
  <App />
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
