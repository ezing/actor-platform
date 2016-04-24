/*
 * Copyright (C) 2015 Actor LLC. <https://actor.im>
 */

package im.actor.core.modules.messaging.history;

import im.actor.core.api.rpc.RequestLoadArchived;
import im.actor.core.api.rpc.ResponseLoadArchived;
import im.actor.core.modules.ModuleContext;
import im.actor.core.network.RpcCallback;
import im.actor.core.network.RpcException;
import im.actor.core.modules.ModuleActor;
import im.actor.runtime.Log;
import im.actor.runtime.actors.messages.Void;
import im.actor.runtime.function.Consumer;

public class ArchivedDialogsActor extends ModuleActor {

    private static final String TAG = "ArchivedDialogsActor";

    private static final int LIMIT = 20;


    private byte[] nextOffset;

    private boolean isLoading = false;

    RpcCallback<ResponseLoadArchived> lastCallback;
    private long lastRequest = -1;

    public ArchivedDialogsActor(ModuleContext context) {
        super(context);
    }

    private void onLoadMore(boolean init, RpcCallback<ResponseLoadArchived> callback) {

        if (init || isLoading) {

            //
            // notify old callback replaced
            //
            if (lastCallback != null) {
                lastCallback.onError(new RpcException(TAG, 0, "callback replaced", false, null));
            }
        }
        lastCallback = callback;

        if (isLoading && !init) {
            return;
        }

        if (init) {
            if (lastRequest != -1) {
                cancelRequest(lastRequest);
            }
            nextOffset = null;
        }

        isLoading = true;

        Log.d(TAG, "Loading archived dialogs");
        lastRequest = request(new RequestLoadArchived(nextOffset, LIMIT),
                new RpcCallback<ResponseLoadArchived>() {
                    @Override
                    public void onResult(final ResponseLoadArchived response) {
                        updates().applyRelatedData(response.getUsers(), response.getGroups()).then(new Consumer<Void>() {
                            @Override
                            public void apply(Void aVoid) {
                                onLoadedMore(response);
                            }
                        });
                    }

                    @Override
                    public void onError(RpcException e) {
                        lastCallback.onError(e);
                    }
                });
    }

    private void onLoadedMore(ResponseLoadArchived responseLoadArchiveds) {
        isLoading = false;

        this.nextOffset = responseLoadArchiveds.getNextOffset();
        lastCallback.onResult(responseLoadArchiveds);
        Log.d(TAG, "Archived dialogs loaded");
    }

    // Messages

    @Override
    public void onReceive(Object message) {
        if (message instanceof LoadMore) {
            onLoadMore(((LoadMore) message).isInit(), ((LoadMore) message).getCallback());
        } else {
            super.onReceive(message);
        }
    }

    public static class LoadMore {
        RpcCallback<ResponseLoadArchived> callback;
        boolean init;

        public LoadMore(boolean init, RpcCallback<ResponseLoadArchived> callback) {
            this.callback = callback;
            this.init = init;
        }

        public boolean isInit() {
            return init;
        }

        public RpcCallback<ResponseLoadArchived> getCallback() {
            return callback;
        }
    }
}
