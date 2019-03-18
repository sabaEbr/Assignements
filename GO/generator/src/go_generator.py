import os
import sys
from ruamel.yaml import YAML
from .java_util import *


class GoGen:

    def __init__(self, program_path):
        # Read generation program
        yaml = YAML()
        with open(program_path, 'r') as program:
            self.program = yaml.load(program)
        self.project_path = self.program['project_path']
        self.prod_path = self.program['prod_path']

        self.switcher = {
            'java': self.java_work,
            'go': self.go_work
        }

    def __call__(self):
        os.chdir(self.project_path)
        for lang in self.program['sequencer']:
            self.switcher.get(lang, self.nothing)(self.program['sequencer'][lang])
        # [self.switcher.get(lang, self.nothing)(self.program['sequencer'][lang]) for lang in self.program['sequencer']]

    def java_work(self, java_sequence):
        for project in java_sequence:
            for key in project.keys():
                exec(key + ' = \'' + str(project[key]) + '\'', globals())

            print(name)
            java_build(name, source_path, java_file, class_output, class_path)
            java_lib(name, class_output, jar_content, self.prod_path, manifest)

    def go_work(self, go_sequence): pass

    def nothing(self): pass


if __name__ == "__main__":
    GoGen(sys.argv[1])()
