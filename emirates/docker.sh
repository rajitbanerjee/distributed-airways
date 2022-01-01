# run everytime when there's a change to this service
docker buildx build \
    --platform linux/amd64,linux/arm64 \
    --push -t distributedairways/emirates .