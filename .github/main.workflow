    # Workflow to create distribution archive
    workflow "Create Archive" {
        on = "push"
        resolves = ["archive"]
    }

    # Filter for tag
    action "tag" {
        uses = "actions/bin/filter@master"
        args = "tag"
    }

    # Install Dependencies
    action "install" {
        uses = "actions/npm@e7aaefe"
        needs = "tag"
        args = "install"
    }

    # Build
    action "build" {
        uses = "actions/npm@e7aaefe"
        needs = ["install"]
        args = "run build"
    }

    # Create Release ZIP archive
    action "archive" {
        uses = "lubusIN/actions/archive@master"
        needs = ["build"]
        env = {
                ZIP_FILENAME = "archive-filename"
            }
    }
