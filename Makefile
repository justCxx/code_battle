build:
		lein cljsbuild once

install:
		cp profiles.clj.sample profiles.clj
		lein run migrate
