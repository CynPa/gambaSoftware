/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import javax.persistence.Column;
/*   4:    */ import javax.persistence.Entity;
/*   5:    */ import javax.persistence.GeneratedValue;
/*   6:    */ import javax.persistence.GenerationType;
/*   7:    */ import javax.persistence.Id;
/*   8:    */ import javax.persistence.Table;
/*   9:    */ import javax.persistence.TableGenerator;
/*  10:    */ import javax.validation.constraints.NotNull;
/*  11:    */ import javax.validation.constraints.Size;
/*  12:    */ 
/*  13:    */ @Entity
/*  14:    */ @Table(name="canal", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  15:    */ public class Canal
/*  16:    */   extends EntidadBase
/*  17:    */ {
/*  18:    */   private static final long serialVersionUID = 1L;
/*  19:    */   @Id
/*  20:    */   @TableGenerator(name="canal", initialValue=0, allocationSize=50)
/*  21:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="canal")
/*  22:    */   @Column(name="id_canal")
/*  23:    */   private int idCanal;
/*  24:    */   @Column(name="id_organizacion", nullable=false)
/*  25:    */   private int idOrganizacion;
/*  26:    */   @Column(name="id_sucursal", nullable=false)
/*  27:    */   private int idSucursal;
/*  28:    */   @Column(name="codigo", nullable=false, length=10)
/*  29:    */   @Size(min=2, max=10)
/*  30:    */   private String codigo;
/*  31:    */   @Column(name="nombre", nullable=false, length=50)
/*  32:    */   @NotNull
/*  33:    */   @Size(min=2, max=50)
/*  34:    */   private String nombre;
/*  35:    */   @Column(name="descripcion", nullable=false)
/*  36:    */   @Size(max=200)
/*  37:    */   private String descripcion;
/*  38:    */   @Column(name="activo", nullable=false)
/*  39:    */   private boolean activo;
/*  40:    */   @Column(name="predeterminado", nullable=false)
/*  41:    */   private boolean predeterminado;
/*  42:    */   
/*  43:    */   public int getIdCanal()
/*  44:    */   {
/*  45:100 */     return this.idCanal;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void setIdCanal(int idCanal)
/*  49:    */   {
/*  50:110 */     this.idCanal = idCanal;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public int getIdOrganizacion()
/*  54:    */   {
/*  55:119 */     return this.idOrganizacion;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setIdOrganizacion(int idOrganizacion)
/*  59:    */   {
/*  60:129 */     this.idOrganizacion = idOrganizacion;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public int getIdSucursal()
/*  64:    */   {
/*  65:138 */     return this.idSucursal;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setIdSucursal(int idSucursal)
/*  69:    */   {
/*  70:148 */     this.idSucursal = idSucursal;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String getNombre()
/*  74:    */   {
/*  75:157 */     return this.nombre;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setNombre(String nombre)
/*  79:    */   {
/*  80:167 */     this.nombre = nombre;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String getCodigo()
/*  84:    */   {
/*  85:176 */     return this.codigo;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setCodigo(String codigo)
/*  89:    */   {
/*  90:186 */     this.codigo = codigo;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String getDescripcion()
/*  94:    */   {
/*  95:195 */     return this.descripcion;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setDescripcion(String descripcion)
/*  99:    */   {
/* 100:205 */     this.descripcion = descripcion;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public boolean isActivo()
/* 104:    */   {
/* 105:214 */     return this.activo;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setActivo(boolean activo)
/* 109:    */   {
/* 110:224 */     this.activo = activo;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public boolean isPredeterminado()
/* 114:    */   {
/* 115:233 */     return this.predeterminado;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setPredeterminado(boolean predeterminado)
/* 119:    */   {
/* 120:243 */     this.predeterminado = predeterminado;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public int getId()
/* 124:    */   {
/* 125:253 */     return this.idCanal;
/* 126:    */   }
/* 127:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Canal
 * JD-Core Version:    0.7.0.1
 */