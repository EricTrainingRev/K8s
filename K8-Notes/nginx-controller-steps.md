1. Ensure Helm has the up to date ingress-nginx repo
```cli
helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx
helm repo update
```

2. Install the chart in your cluster
```cli
helm install [RELEASE_NAME] ingress-nginx/ingress-nginx
```

3. Deploy a controller for your application (it will need ingress rules  to work propperly)
```cli
helm upgrade --install ingress-nginx ingress-nginx --repo https://kubernetes.github.io/ingress-nginx
```