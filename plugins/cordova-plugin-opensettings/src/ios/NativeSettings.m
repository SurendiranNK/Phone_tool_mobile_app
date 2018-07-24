#import "NativeSettings.h"

@implementation NativeSettings

- (BOOL)openSchema:(NSString *)scheme {
    NSURL *url = [NSURL URLWithString:scheme];
    UIApplication *application = [UIApplication sharedApplication];
    if ([application canOpenURL:url]) {
        [application openURL:url options:@{} completionHandler:^(BOOL success) {
            NSLog(@"Open %@: %d", scheme, success);
        }];
        return YES;
    }
    return NO;
}

- (BOOL)openSettings:(NSString *)pref {
    NSArray *settings = @[
            [NSString stringWithFormat:@"App-Prefs:%@", pref],
            [NSString stringWithFormat:@"prefs:%@", pref]
    ];
    for (NSString *s in settings) {
        if ([self openSchema:s]) {
            return YES;
        }
    }
    return NO;
}

- (void)open:(CDVInvokedUrlCommand *)command {
    CDVPluginResult *pluginResult = nil;
    NSString *key = command.arguments[0];
    BOOL result = NO;

    if ([key isEqualToString:@"application_details"]) {
        result = [self openSchema:UIApplicationOpenSettingsURLString];
    } else if ([key isEqualToString:@"settings"]) {
        result = [self openSettings:@"root"];
    } else if ([key isEqualToString:@"about"]) {
        result = [self openSettings:@"root=General&path=About"];
    } else if ([key isEqualToString:@"accessibility"]) {
        result = [self openSettings:@"root=General&path=ACCESSIBILITY"];
    } else if ([key isEqualToString:@"account"]) {
        result = [self openSettings:@"root=ACCOUNT_SETTINGS"];
    } else if ([key isEqualToString:@"airplane_mode"]) {
        result = [self openSettings:@"root=AIRPLANE_MODE"];
    } else if ([key isEqualToString:@"autolock"]) {
        result = [self openSettings:@"root=General&path=AUTOLOCK"];
    } else if ([key isEqualToString:@"display"]) {
        result = [self openSettings:@"root=Brightness"];
    } else if ([key isEqualToString:@"bluetooth"]) {
        result = [self openSettings:@"root=Bluetooth"];
    } else if ([key isEqualToString:@"castle"]) {
        result = [self openSettings:@"root=CASTLE"];
    } else if ([key isEqualToString:@"cellular_usage"]) {
        result = [self openSettings:@"root=General&path=USAGE/CELLULAR_USAGE"];
    } else if ([key isEqualToString:@"configuration_list"]) {
        result = [self openSettings:@"root=General&path=ManagedConfigurationList"];
    } else if ([key isEqualToString:@"date"]) {
        result = [self openSettings:@"root=General&path=DATE_AND_TIME"];
    } else if ([key isEqualToString:@"facetime"]) {
        result = [self openSettings:@"root=FACETIME"];
    } else if ([key isEqualToString:@"settings"]) {
        result = [self openSettings:@"root=General"];
    } else if ([key isEqualToString:@"tethering"]) {
        result = [self openSettings:@"root=INTERNET_TETHERING"];
    } else if ([key isEqualToString:@"music"]) {
        result = [self openSettings:@"root=MUSIC"];
    } else if ([key isEqualToString:@"music_equalizer"]) {
        result = [self openSettings:@"root=MUSIC&path=EQ"];
    } else if ([key isEqualToString:@"music_volume"]) {
        result = [self openSettings:@"root=MUSIC&path=VolumeLimit"];
    } else if ([key isEqualToString:@"keyboard"]) {
        result = [self openSettings:@"root=General&path=Keyboard"];
    } else if ([key isEqualToString:@"locale"]) {
        result = [self openSettings:@"root=General&path=INTERNATIONAL"];
    } else if ([key isEqualToString:@"location"]) {
        result = [self openSettings:@"root=LOCATION_SERVICES"];
    } else if ([key isEqualToString:@"network"]) {
        result = [self openSettings:@"root=General&path=Network"];
    } else if ([key isEqualToString:@"nike_ipod"]) {
        result = [self openSettings:@"root=NIKE_PLUS_IPOD"];
    } else if ([key isEqualToString:@"notes"]) {
        result = [self openSettings:@"root=NOTES"];
    } else if ([key isEqualToString:@"notification_id"]) {
        result = [self openSettings:@"root=NOTIFICATIONS_ID"];
    } else if ([key isEqualToString:@"passbook"]) {
        result = [self openSettings:@"root=PASSBOOK"];
    } else if ([key isEqualToString:@"phone"]) {
        result = [self openSettings:@"root=Phone"];
    } else if ([key isEqualToString:@"photos"]) {
        result = [self openSettings:@"root=Photos"];
    } else if ([key isEqualToString:@"reset"]) {
        result = [self openSettings:@"root=General&path=Reset"];
    } else if ([key isEqualToString:@"ringtone"]) {
        result = [self openSettings:@"root=Sounds&path=Ringtone"];
    } else if ([key isEqualToString:@"browser"]) {
        result = [self openSettings:@"root=Safari"];
    } else if ([key isEqualToString:@"search"]) {
        result = [self openSettings:@"root=General&path=Assistant"];
    } else if ([key isEqualToString:@"sound"]) {
        result = [self openSettings:@"root=Sounds"];
    } else if ([key isEqualToString:@"software_update"]) {
        result = [self openSettings:@"root=General&path=SOFTWARE_UPDATE_LINK"];
    } else if ([key isEqualToString:@"storage"]) {
        result = [self openSettings:@"root=CASTLE&path=STORAGE_AND_BACKUP"];
    } else if ([key isEqualToString:@"store"]) {
        result = [self openSettings:@"root=STORE"];
    } else if ([key isEqualToString:@"usage"]) {
        result = [self openSettings:@"root=General&path=USAGE"];
    } else if ([key isEqualToString:@"video"]) {
        result = [self openSettings:@"root=VIDEO"];
    } else if ([key isEqualToString:@"vpn"]) {
        result = [self openSettings:@"root=General&path=Network/VPN"];
    } else if ([key isEqualToString:@"wallpaper"]) {
        result = [self openSettings:@"root=Wallpaper"];
    } else if ([key isEqualToString:@"wifi"]) {
        result = [self openSettings:@"root=WIFI"];
    } else {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Invalid Action"];
    }

    if (!pluginResult) {
        if (result) {
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"Opened"];
        } else {
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Cannot open"];
        }
    }

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

@end
