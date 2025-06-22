import { writeFileSync, readFileSync, existsSync } from 'fs';
import { join } from 'path';

interface FirebaseConfig {
  apiKey: string;
  authDomain: string;
  projectId: string;
  storageBucket: string;
  messagingSenderId?: string;
  appId: string;
}

const CONFIG_FILE = join(process.cwd(), 'firebase-config.json');

export function loadFirebaseConfig(): FirebaseConfig {
  // First, try to load from environment variables (using your Firebase secrets)
  const envConfig = {
    apiKey: process.env.FIREBASE_API_KEY,
    authDomain: process.env.FIREBASE_AUTH_DOMAIN,
    projectId: process.env.FIREBASE_PROJECT_ID,
    storageBucket: process.env.FIREBASE_STORAGE_BUCKET,
    messagingSenderId: process.env.FIREBASE_MESSAGING_SENDER_ID,
    appId: process.env.FIREBASE_APP_ID,
  };

  // If we have env variables, save them to config file and use them
  if (envConfig.apiKey && envConfig.projectId && envConfig.appId) {
    const fullConfig: FirebaseConfig = {
      apiKey: envConfig.apiKey,
      authDomain: envConfig.authDomain || `${envConfig.projectId}.firebaseapp.com`,
      projectId: envConfig.projectId,
      storageBucket: envConfig.storageBucket || `${envConfig.projectId}.appspot.com`,
      messagingSenderId: envConfig.messagingSenderId,
      appId: envConfig.appId,
    };

    // Save to config file for future use
    writeFileSync(CONFIG_FILE, JSON.stringify(fullConfig, null, 2));
    console.log('✓ Firebase config loaded from environment variables');
    return fullConfig;
  }

  // If no env variables, try to load from config file
  if (existsSync(CONFIG_FILE)) {
    try {
      const configData = readFileSync(CONFIG_FILE, 'utf8');
      const config = JSON.parse(configData) as FirebaseConfig;
      
      if (config.apiKey && config.projectId && config.appId) {
        console.log('✓ Firebase config loaded from firebase-config.json');
        return config;
      }
    } catch (error) {
      console.error('Error reading firebase-config.json:', error);
    }
  }

  // Fallback to demo config if nothing else works
  console.warn('⚠ Using demo Firebase config - please update firebase-config.json with your credentials');
  return {
    apiKey: "demo-api-key",
    authDomain: "demo-project.firebaseapp.com",
    projectId: "demo-project",
    storageBucket: "demo-project.firebasestorage.app",
    appId: "demo-app-id",
  };
}

export function updateFirebaseConfig(config: Partial<FirebaseConfig>): void {
  let currentConfig: FirebaseConfig;
  
  if (existsSync(CONFIG_FILE)) {
    try {
      const configData = readFileSync(CONFIG_FILE, 'utf8');
      currentConfig = JSON.parse(configData);
    } catch {
      currentConfig = loadFirebaseConfig();
    }
  } else {
    currentConfig = loadFirebaseConfig();
  }

  const updatedConfig = { ...currentConfig, ...config };
  writeFileSync(CONFIG_FILE, JSON.stringify(updatedConfig, null, 2));
  console.log('✓ Firebase config updated');
}