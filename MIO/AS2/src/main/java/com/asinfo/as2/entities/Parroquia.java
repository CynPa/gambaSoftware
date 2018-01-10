/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import javax.persistence.Column;
/*   4:    */ import javax.persistence.Entity;
/*   5:    */ import javax.persistence.FetchType;
/*   6:    */ import javax.persistence.GeneratedValue;
/*   7:    */ import javax.persistence.GenerationType;
/*   8:    */ import javax.persistence.Id;
/*   9:    */ import javax.persistence.JoinColumn;
/*  10:    */ import javax.persistence.ManyToOne;
/*  11:    */ import javax.persistence.Table;
/*  12:    */ import javax.persistence.TableGenerator;
/*  13:    */ import javax.validation.constraints.NotNull;
/*  14:    */ import javax.validation.constraints.Size;
/*  15:    */ 
/*  16:    */ @Entity
/*  17:    */ @Table(name="parroquia")
/*  18:    */ public class Parroquia
/*  19:    */   extends EntidadBase
/*  20:    */ {
/*  21:    */   private static final long serialVersionUID = 2977213840972094356L;
/*  22:    */   @Id
/*  23:    */   @TableGenerator(name="parroquia", initialValue=0, allocationSize=50)
/*  24:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="parroquia")
/*  25:    */   @Column(name="id_parroquia")
/*  26:    */   private int idParroquia;
/*  27:    */   @Column(name="id_organizacion", nullable=false)
/*  28:    */   private int idOrganizacion;
/*  29:    */   @Column(name="id_sucursal", nullable=false)
/*  30:    */   private int idSucursal;
/*  31:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  32:    */   @JoinColumn(name="id_ciudad", nullable=true)
/*  33:    */   private Ciudad ciudad;
/*  34:    */   @Column(name="codigo", length=10, nullable=false)
/*  35:    */   @NotNull
/*  36:    */   @Size(min=2, max=10)
/*  37:    */   private String codigo;
/*  38:    */   @Column(name="nombre", length=100, nullable=false)
/*  39:    */   @NotNull
/*  40:    */   @Size(min=2, max=100)
/*  41:    */   private String nombre;
/*  42:    */   @Column(name="descripcion", length=200, nullable=true)
/*  43:    */   @Size(max=200)
/*  44:    */   private String descripcion;
/*  45:    */   @Column(name="activo", nullable=false)
/*  46:    */   private boolean activo;
/*  47:    */   @Column(name="predeterminado", nullable=false)
/*  48:    */   private boolean predeterminado;
/*  49:    */   
/*  50:    */   public int getId()
/*  51:    */   {
/*  52: 69 */     return this.idParroquia;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public int getIdParroquia()
/*  56:    */   {
/*  57: 75 */     return this.idParroquia;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setIdParroquia(int idParroquia)
/*  61:    */   {
/*  62: 81 */     this.idParroquia = idParroquia;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int getIdOrganizacion()
/*  66:    */   {
/*  67: 87 */     return this.idOrganizacion;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setIdOrganizacion(int idOrganizacion)
/*  71:    */   {
/*  72: 93 */     this.idOrganizacion = idOrganizacion;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int getIdSucursal()
/*  76:    */   {
/*  77: 99 */     return this.idSucursal;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setIdSucursal(int idSucursal)
/*  81:    */   {
/*  82:105 */     this.idSucursal = idSucursal;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String getCodigo()
/*  86:    */   {
/*  87:111 */     return this.codigo;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setCodigo(String codigo)
/*  91:    */   {
/*  92:117 */     this.codigo = codigo;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public String getNombre()
/*  96:    */   {
/*  97:123 */     return this.nombre;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setNombre(String nombre)
/* 101:    */   {
/* 102:129 */     this.nombre = nombre;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String getDescripcion()
/* 106:    */   {
/* 107:135 */     return this.descripcion;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setDescripcion(String descripcion)
/* 111:    */   {
/* 112:141 */     this.descripcion = descripcion;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public boolean isActivo()
/* 116:    */   {
/* 117:147 */     return this.activo;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setActivo(boolean activo)
/* 121:    */   {
/* 122:153 */     this.activo = activo;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public boolean isPredeterminado()
/* 126:    */   {
/* 127:159 */     return this.predeterminado;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setPredeterminado(boolean predeterminado)
/* 131:    */   {
/* 132:165 */     this.predeterminado = predeterminado;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public Ciudad getCiudad()
/* 136:    */   {
/* 137:171 */     return this.ciudad;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setCiudad(Ciudad ciudad)
/* 141:    */   {
/* 142:177 */     this.ciudad = ciudad;
/* 143:    */   }
/* 144:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Parroquia
 * JD-Core Version:    0.7.0.1
 */