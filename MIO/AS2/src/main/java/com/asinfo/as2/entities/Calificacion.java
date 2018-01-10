/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import javax.persistence.Column;
/*   4:    */ import javax.persistence.Entity;
/*   5:    */ import javax.persistence.GeneratedValue;
/*   6:    */ import javax.persistence.GenerationType;
/*   7:    */ import javax.persistence.Id;
/*   8:    */ import javax.persistence.Table;
/*   9:    */ import javax.persistence.TableGenerator;
/*  10:    */ import javax.validation.constraints.Min;
/*  11:    */ import javax.validation.constraints.NotNull;
/*  12:    */ import javax.validation.constraints.Size;
/*  13:    */ 
/*  14:    */ @Entity
/*  15:    */ @Table(name="calificacion")
/*  16:    */ public class Calificacion
/*  17:    */   extends EntidadBase
/*  18:    */ {
/*  19:    */   private static final long serialVersionUID = 1L;
/*  20:    */   @Id
/*  21:    */   @TableGenerator(name="calificacion", initialValue=0, allocationSize=50)
/*  22:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="calificacion")
/*  23:    */   @Column(name="calificacion")
/*  24:    */   private int idCalificacion;
/*  25:    */   @Column(name="id_organizacion", nullable=false)
/*  26:    */   private int idOrganizacion;
/*  27:    */   @Column(name="id_sucursal", nullable=false)
/*  28:    */   private int idSucursal;
/*  29:    */   @Column(name="codigo", nullable=false, length=10)
/*  30:    */   @NotNull
/*  31:    */   @Size(min=1, max=10)
/*  32:    */   private String codigo;
/*  33:    */   @Column(name="nombre", length=50, nullable=false)
/*  34:    */   @NotNull
/*  35:    */   @Size(min=2, max=50)
/*  36:    */   private String nombre;
/*  37:    */   @Column(name="descripcion", length=200)
/*  38:    */   @Size(max=200)
/*  39:    */   private String descripcion;
/*  40:    */   @Column(name="activo", nullable=false)
/*  41:    */   private boolean activo;
/*  42:    */   @Column(name="predeterminado", nullable=false)
/*  43:    */   private boolean predeterminado;
/*  44:    */   @Min(0L)
/*  45:    */   @Column(name="rango_desde", nullable=false)
/*  46:    */   private int rangoDesde;
/*  47:    */   @Min(0L)
/*  48:    */   @Column(name="rango_hasta", nullable=false)
/*  49:    */   private int rangoHasta;
/*  50:    */   
/*  51:    */   public int getId()
/*  52:    */   {
/*  53: 50 */     return this.idCalificacion;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int getIdCalificacion()
/*  57:    */   {
/*  58: 54 */     return this.idCalificacion;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setIdCalificacion(int idCalificacion)
/*  62:    */   {
/*  63: 58 */     this.idCalificacion = idCalificacion;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int getIdOrganizacion()
/*  67:    */   {
/*  68: 62 */     return this.idOrganizacion;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setIdOrganizacion(int idOrganizacion)
/*  72:    */   {
/*  73: 66 */     this.idOrganizacion = idOrganizacion;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public int getIdSucursal()
/*  77:    */   {
/*  78: 70 */     return this.idSucursal;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setIdSucursal(int idSucursal)
/*  82:    */   {
/*  83: 74 */     this.idSucursal = idSucursal;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String getCodigo()
/*  87:    */   {
/*  88: 78 */     return this.codigo;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setCodigo(String codigo)
/*  92:    */   {
/*  93: 82 */     this.codigo = codigo;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String getNombre()
/*  97:    */   {
/*  98: 86 */     return this.nombre;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setNombre(String nombre)
/* 102:    */   {
/* 103: 90 */     this.nombre = nombre;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String getDescripcion()
/* 107:    */   {
/* 108: 94 */     return this.descripcion;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setDescripcion(String descripcion)
/* 112:    */   {
/* 113: 98 */     this.descripcion = descripcion;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public boolean isActivo()
/* 117:    */   {
/* 118:102 */     return this.activo;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setActivo(boolean activo)
/* 122:    */   {
/* 123:106 */     this.activo = activo;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public boolean isPredeterminado()
/* 127:    */   {
/* 128:110 */     return this.predeterminado;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setPredeterminado(boolean predeterminado)
/* 132:    */   {
/* 133:114 */     this.predeterminado = predeterminado;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public int getRangoDesde()
/* 137:    */   {
/* 138:118 */     return this.rangoDesde;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setRangoDesde(int rangoDesde)
/* 142:    */   {
/* 143:122 */     this.rangoDesde = rangoDesde;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public int getRangoHasta()
/* 147:    */   {
/* 148:126 */     return this.rangoHasta;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setRangoHasta(int rangoHasta)
/* 152:    */   {
/* 153:130 */     this.rangoHasta = rangoHasta;
/* 154:    */   }
/* 155:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Calificacion
 * JD-Core Version:    0.7.0.1
 */