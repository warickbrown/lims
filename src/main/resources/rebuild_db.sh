#!/bin/bash
# Make sure password is in: %APPDATA%\postgresql\pgpass.conf or ~/.pgpass (0600 permissions)
# It should be in this format: localhost:5432:*:curtin_lims_user:password
psql -h localhost -U curtin_lims_user -d curtin_lims -f schema.sql
psql -h localhost -U curtin_lims_user -d curtin_lims -f test-data.sql
