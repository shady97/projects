import dotenv from 'dotenv';

dotenv.config();

export default {
  PORT: process.env.PORT || 5000,
  MONGODB_URL: 'mongodb+srv://shady97:udwdhkmot9VDcH6y@shopping.juggx.mongodb.net/ecommerce?retryWrites=true&w=majority',
  JWT_SECRET: process.env.JWT_SECRET || 'somethingsecret',
  PAYPAL_CLIENT_ID: process.env.PAYPAL_CLIENT_ID || 'sb',
  accessKeyId: process.env.accessKeyId || 'accessKeyId',
  secretAccessKey: process.env.secretAccessKey || 'secretAccessKey',
};
