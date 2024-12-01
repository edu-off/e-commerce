docker-start:
	@echo "subindo containeres das aplicacoes e bancos de dados"
	@docker-compose -f docker-compose.yaml up -d