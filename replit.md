# Pizza Ordering System - Architecture Overview

## Overview

This is a modern full-stack pizza ordering application built with React, Express.js, and multiple data storage options. The system provides both customer-facing ordering capabilities and employee management tools for running a pizza restaurant business.

## System Architecture

### Frontend Architecture
- **Framework**: React 18 with TypeScript
- **Routing**: Wouter for client-side routing
- **State Management**: React Query (TanStack Query) for server state, React hooks for local state
- **UI Framework**: Radix UI components with shadcn/ui design system
- **Styling**: Tailwind CSS with custom CSS variables for theming
- **Build Tool**: Vite for development and production builds

### Backend Architecture
- **Runtime**: Node.js with Express.js server
- **Language**: TypeScript throughout
- **API Design**: RESTful API with rate limiting and CORS support
- **Development**: Hot reload with tsx and Vite HMR integration

### Data Storage Solutions
The application implements a flexible storage abstraction layer supporting three different backends:

1. **Database Storage** (Primary)
   - PostgreSQL with Drizzle ORM
   - Neon serverless database integration
   - Structured schema with relations

2. **Firebase Storage** (Alternative)
   - Firestore for real-time data synchronization
   - Firebase Authentication integration
   - Real-time order updates

3. **Persistent File Storage** (Fallback)
   - JSON file-based storage for development
   - Local data persistence without database requirements

## Key Components

### Customer Experience
- **Pizza Menu**: Dynamic pizza catalog with categories and customization
- **Pizza Builder**: Interactive pizza customization interface
- **Shopping Cart**: Persistent cart with local storage
- **Checkout Flow**: Multi-step checkout with customer information
- **Order Tracking**: Real-time order status updates
- **Payment Integration**: Stripe payment processing

### Employee Experience
- **Kitchen Display System**: Real-time order management dashboard
- **Order Management**: Status updates, timing controls, and completion tracking
- **Customer Communication**: Notification system with SMS/email capabilities
- **Analytics Dashboard**: Sales metrics, order statistics, and performance data
- **Trust System**: Customer reliability scoring for cash payments

### Mobile Support
- **PWA Capabilities**: Progressive Web App with offline support
- **Mobile Optimization**: Responsive design optimized for mobile devices
- **Push Notifications**: Real-time notifications for order updates
- **Offline Functionality**: Background sync and cached data

## Data Flow

### Order Processing Flow
1. Customer browses menu and builds pizza
2. Items added to cart with local storage persistence
3. Checkout process collects customer information
4. Payment processed through Stripe integration
5. Order stored in database with unique tracking ID
6. Real-time sync to Firebase for employee dashboard
7. Employee receives order notification
8. Order status updates propagated to customer
9. Completion triggers customer notification

### Authentication Flow
- Firebase Authentication for user management
- Session persistence across browser refreshes
- Optional guest checkout for faster ordering
- Employee authentication with simple credentials

## External Dependencies

### Payment Processing
- **Stripe**: Credit card processing and payment flows
- **Payment Methods**: Card payments with future cash payment support

### Communication Services
- **Email Notifications**: Customer order confirmations and updates
- **SMS Integration**: Ready for SMS notifications (implementation pending)
- **Push Notifications**: Browser and mobile app notifications

### Development Tools
- **Replit Integration**: Cloud development environment support
- **Hot Module Replacement**: Real-time development updates
- **TypeScript**: Full type safety across frontend and backend

## Deployment Strategy

The application is configured for multiple deployment scenarios:

### Development
- Local development with Vite dev server
- Hot reload and error overlay
- File-based storage for rapid prototyping

### Production
- Static frontend build served by Express
- Optimized bundle with tree shaking
- Database or Firebase backend
- Auto-scaling deployment target

### Environment Configuration
- Environment variables for sensitive configuration
- Fallback configuration files for Firebase
- Flexible storage backend selection

## Recent Changes
- June 22, 2025: Successfully migrated project from Replit Agent to standard Replit environment
- June 22, 2025: Verified all dependencies installed and server running on port 5000  
- June 22, 2025: Fixed Android app configuration - converted from native Compose to WebView approach
- June 22, 2025: Created working Android employee dashboard app that loads web interface
- June 22, 2025: Added Android build scripts and comprehensive setup documentation
- June 22, 2025: Configured Firebase as primary database with user's project credentials
- June 22, 2025: Successfully connected Firebase Firestore for all user and order data
- June 22, 2025: Added PostgreSQL database as secondary storage option with full schema

## Changelog
- June 22, 2025: Initial setup
- January 22, 2025: Project migration completed

## User Preferences

Preferred communication style: Simple, everyday language.
Android development: Planning native Android app to complement web application

## Firebase Configuration
Project: pizza-8ef42
- All Firebase credentials permanently saved in secure environment variables
- Configuration automatically loads on server restart
- Backup saved to firebase-config.json
- Ready for production deployment