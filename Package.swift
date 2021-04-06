// swift-tools-version:5.3
import PackageDescription

let package = Package(
    name: "KMMSample2",
    platforms: [
        .iOS(.v13)
    ],
    products: [
        .library(
            name: "KMMSample2",
            targets: ["KMMSample2"]
        ),
    ],
    targets: [
        .binaryTarget(
            name: "KMMSample2",
            path: "./KMMSample2.xcframework"
        ),
    ]
)
