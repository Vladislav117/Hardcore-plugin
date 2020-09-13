f = open("build/libs/EP.jar", 'rb')
newf = open("C:/Users/User/Desktop/серв/config/mods/EP.jar", "wb")
newf.write(f.read())
f.close()
newf.close()
# import os
# os.startfile('C:/Users/User/Desktop/серв/start.bat')