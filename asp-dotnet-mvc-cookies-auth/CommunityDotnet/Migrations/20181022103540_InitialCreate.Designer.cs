﻿// <auto-generated />
using CommunityDotnet.Data;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;

namespace CommunityDotnet.Migrations
{
    [DbContext(typeof(CommunityDbContext))]
    [Migration("20181022103540_InitialCreate")]
    partial class InitialCreate
    {
        protected override void BuildTargetModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .HasAnnotation("ProductVersion", "2.1.4-rtm-31024")
                .HasAnnotation("Relational:MaxIdentifierLength", 128)
                .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

            modelBuilder.Entity("CommunityDotnet.Data.MessagePoco", b =>
                {
                    b.Property<int>("MessageId")
                        .ValueGeneratedOnAdd()
                        .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

                    b.Property<string>("FromUserUsername");

                    b.Property<bool>("IsSeen");

                    b.Property<string>("TextMessage");

                    b.Property<string>("Timestamp")
                        .IsRequired();

                    b.Property<string>("ToUserUsername");

                    b.HasKey("MessageId");

                    b.HasIndex("FromUserUsername");

                    b.HasIndex("ToUserUsername");

                    b.ToTable("Messages");
                });

            modelBuilder.Entity("CommunityDotnet.Data.UserPoco", b =>
                {
                    b.Property<string>("Username")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("LatestLogin");

                    b.Property<int>("NumberOfLoginsInMonth");

                    b.Property<int>("NumberOfMessageRecv");

                    b.Property<int>("NumberOfMessageSent");

                    b.Property<int>("NumberOfUnreadMessages");

                    b.Property<string>("Password")
                        .IsRequired();

                    b.HasKey("Username");

                    b.ToTable("Users");
                });

            modelBuilder.Entity("CommunityDotnet.Data.MessagePoco", b =>
                {
                    b.HasOne("CommunityDotnet.Data.UserPoco", "FromUser")
                        .WithMany()
                        .HasForeignKey("FromUserUsername");

                    b.HasOne("CommunityDotnet.Data.UserPoco", "ToUser")
                        .WithMany()
                        .HasForeignKey("ToUserUsername");
                });
#pragma warning restore 612, 618
        }
    }
}