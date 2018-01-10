/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import javax.persistence.Column;
/*   4:    */ import javax.persistence.Entity;
/*   5:    */ import javax.persistence.GeneratedValue;
/*   6:    */ import javax.persistence.GenerationType;
/*   7:    */ import javax.persistence.Id;
/*   8:    */ import javax.persistence.Table;
/*   9:    */ import javax.persistence.TableGenerator;
/*  10:    */ import javax.validation.constraints.Size;
/*  11:    */ 
/*  12:    */ @Entity
/*  13:    */ @Table(name="modulo", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "nombre"})})
/*  14:    */ public class Modulo
/*  15:    */   extends EntidadBase
/*  16:    */ {
/*  17:    */   private static final long serialVersionUID = 1L;
/*  18:    */   @Id
/*  19:    */   @TableGenerator(name="modulo", initialValue=0, allocationSize=50)
/*  20:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="modulo")
/*  21:    */   @Column(name="id_modulo")
/*  22:    */   private int idModuloMenu;
/*  23:    */   @Column(name="id_organizacion", nullable=false)
/*  24:    */   private int idOrganizacion;
/*  25:    */   @Column(name="id_sucursal", nullable=false)
/*  26:    */   private int idSucursal;
/*  27:    */   @Column(name="nombre", length=50)
/*  28:    */   @Size(min=2, max=50)
/*  29:    */   private String nombre;
/*  30:    */   @Column(name="orden", nullable=false)
/*  31:    */   private int orden;
/*  32:    */   
/*  33:    */   public int getId()
/*  34:    */   {
/*  35: 79 */     return this.idModuloMenu;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public int getIdModuloMenu()
/*  39:    */   {
/*  40: 87 */     return this.idModuloMenu;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void setIdModuloMenu(int idModuloMenu)
/*  44:    */   {
/*  45: 95 */     this.idModuloMenu = idModuloMenu;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public int getIdOrganizacion()
/*  49:    */   {
/*  50:103 */     return this.idOrganizacion;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void setIdOrganizacion(int idOrganizacion)
/*  54:    */   {
/*  55:111 */     this.idOrganizacion = idOrganizacion;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int getIdSucursal()
/*  59:    */   {
/*  60:119 */     return this.idSucursal;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setIdSucursal(int idSucursal)
/*  64:    */   {
/*  65:127 */     this.idSucursal = idSucursal;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String getNombre()
/*  69:    */   {
/*  70:135 */     return this.nombre;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setNombre(String nombre)
/*  74:    */   {
/*  75:143 */     this.nombre = nombre;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public int getOrden()
/*  79:    */   {
/*  80:151 */     return this.orden;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setOrden(int orden)
/*  84:    */   {
/*  85:159 */     this.orden = orden;
/*  86:    */   }
/*  87:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Modulo
 * JD-Core Version:    0.7.0.1
 */