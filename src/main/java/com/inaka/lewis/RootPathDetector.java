package com.inaka.lewis;

import com.android.annotations.NonNull;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Location;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;

import lombok.ast.AstVisitor;
import lombok.ast.ClassDeclaration;
import lombok.ast.ForwardingAstVisitor;


public class RootPathDetector extends Detector implements Detector.JavaScanner {

    public static final Issue ISSUE = Issue.create(
            "RootPathDetector",
            "Class should not be inside root package",
            "All classes should be inside a custom package inside the root package.",
            Category.CORRECTNESS,
            2,
            Severity.WARNING,
            new Implementation(RootPathDetector.class, Scope.JAVA_FILE_SCOPE));

    @Override
    public AstVisitor createJavaVisitor(@NonNull final JavaContext context) {
        return new ForwardingAstVisitor() {
            @Override
            public boolean visitClassDeclaration(ClassDeclaration node) {
                String packageName = context.getMainProject().getPackage();
                Location nodeLocation = context.getLocation(node);

                String classLocationString = nodeLocation.getFile().toString().replaceAll("/", ".");
                String fileName = node.astName().astValue();

                int findPackage = classLocationString.lastIndexOf(packageName);
                String filePackageString = classLocationString.substring(findPackage);
                String previousPath = classLocationString.substring(0, findPackage);

                if (filePackageString.equals(packageName + "." + fileName + ".java")
                        && !previousPath.contains("generated")) {
                    context.report(ISSUE, nodeLocation,
                            " Expecting " + fileName + " not to be in root package " + packageName);
                }

                return super.visitClassDeclaration(node);
            }
        };
    }

}