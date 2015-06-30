//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/ex3ndr/Develop/actor-platform/actor-apps/core/src/main/java/im/actor/model/network/mtp/AuthIdRetriever.java
//

#ifndef _MTAuthIdRetriever_H_
#define _MTAuthIdRetriever_H_

#include "J2ObjC_header.h"

@class AMEndpoints;
@protocol AMNetworkProvider;
@protocol MTAuthIdRetriever_AuthIdCallback;

@interface MTAuthIdRetriever : NSObject

#pragma mark Public

- (instancetype)init;

+ (void)requestAuthIdWithAMEndpoints:(AMEndpoints *)endpoints
               withAMNetworkProvider:(id<AMNetworkProvider>)networkProvider
                             withInt:(jint)minDelay
                             withInt:(jint)maxDelay
                             withInt:(jint)maxFailureCount
withMTAuthIdRetriever_AuthIdCallback:(id<MTAuthIdRetriever_AuthIdCallback>)callback;

@end

J2OBJC_EMPTY_STATIC_INIT(MTAuthIdRetriever)

FOUNDATION_EXPORT void MTAuthIdRetriever_requestAuthIdWithAMEndpoints_withAMNetworkProvider_withInt_withInt_withInt_withMTAuthIdRetriever_AuthIdCallback_(AMEndpoints *endpoints, id<AMNetworkProvider> networkProvider, jint minDelay, jint maxDelay, jint maxFailureCount, id<MTAuthIdRetriever_AuthIdCallback> callback);

FOUNDATION_EXPORT void MTAuthIdRetriever_init(MTAuthIdRetriever *self);

FOUNDATION_EXPORT MTAuthIdRetriever *new_MTAuthIdRetriever_init() NS_RETURNS_RETAINED;

J2OBJC_TYPE_LITERAL_HEADER(MTAuthIdRetriever)

typedef MTAuthIdRetriever ImActorModelNetworkMtpAuthIdRetriever;

@protocol MTAuthIdRetriever_AuthIdCallback < NSObject, JavaObject >

- (void)onSuccessWithLong:(jlong)authId;

- (void)onFailure;

@end

J2OBJC_EMPTY_STATIC_INIT(MTAuthIdRetriever_AuthIdCallback)

J2OBJC_TYPE_LITERAL_HEADER(MTAuthIdRetriever_AuthIdCallback)

#endif // _MTAuthIdRetriever_H_
