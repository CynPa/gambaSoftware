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
/*  17:    */ @Table(name="ubicacion_bodega")
/*  18:    */ public class UbicacionBodega
/*  19:    */   extends EntidadBase
/*  20:    */ {
/*  21:    */   private static final long serialVersionUID = 1L;
/*  22:    */   @Id
/*  23:    */   @TableGenerator(name="ubicacion_bodega", initialValue=0, allocationSize=50)
/*  24:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="ubicacion_bodega")
/*  25:    */   @Column(name="id_ubicacion_bodega")
/*  26:    */   private int idUbicacionBodega;
/*  27:    */   @Column(name="id_organizacion", nullable=false)
/*  28:    */   private int idOrganizacion;
/*  29:    */   @Column(name="id_sucursal", nullable=false)
/*  30:    */   private int idSucursal;
/*  31:    */   @Column(name="codigo", length=10, nullable=false)
/*  32:    */   @NotNull
/*  33:    */   @Size(min=2, max=10)
/*  34:    */   private String codigo;
/*  35:    */   @Column(name="posicionX", length=10, nullable=false)
/*  36:    */   @NotNull
/*  37:    */   @Size(min=1, max=10)
/*  38:    */   private String posicionX;
/*  39:    */   @Column(name="posicionY", length=10, nullable=false)
/*  40:    */   @NotNull
/*  41:    */   @Size(min=1, max=10)
/*  42:    */   private String posicionY;
/*  43:    */   @Column(name="posicionZ", length=10, nullable=false)
/*  44:    */   @NotNull
/*  45:    */   @Size(min=1, max=10)
/*  46:    */   private String posicionZ;
/*  47:    */   @Column(name="activo", nullable=false)
/*  48:    */   private boolean activo;
/*  49:    */   @Column(name="predeterminado", nullable=false)
/*  50:    */   private boolean predeterminado;
/*  51:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  52:    */   @JoinColumn(name="id_bodega")
/*  53:    */   private Bodega bodega;
/*  54:    */   
/*  55:    */   public int getIdOrganizacion()
/*  56:    */   {
/*  57: 86 */     return this.idOrganizacion;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setIdOrganizacion(int idOrganizacion)
/*  61:    */   {
/*  62: 96 */     this.idOrganizacion = idOrganizacion;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int getIdSucursal()
/*  66:    */   {
/*  67:105 */     return this.idSucursal;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setIdSucursal(int idSucursal)
/*  71:    */   {
/*  72:115 */     this.idSucursal = idSucursal;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public String getCodigo()
/*  76:    */   {
/*  77:124 */     return this.codigo;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setCodigo(String codigo)
/*  81:    */   {
/*  82:134 */     this.codigo = codigo;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String getPosicionX()
/*  86:    */   {
/*  87:143 */     return this.posicionX;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setPosicionX(String posicionX)
/*  91:    */   {
/*  92:153 */     this.posicionX = posicionX;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public String getPosicionY()
/*  96:    */   {
/*  97:162 */     return this.posicionY;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setPosicionY(String posicionY)
/* 101:    */   {
/* 102:172 */     this.posicionY = posicionY;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String getPosicionZ()
/* 106:    */   {
/* 107:181 */     return this.posicionZ;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setPosicionZ(String posicionZ)
/* 111:    */   {
/* 112:191 */     this.posicionZ = posicionZ;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public boolean isActivo()
/* 116:    */   {
/* 117:200 */     return this.activo;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setActivo(boolean activo)
/* 121:    */   {
/* 122:210 */     this.activo = activo;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public boolean isPredeterminado()
/* 126:    */   {
/* 127:219 */     return this.predeterminado;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setPredeterminado(boolean predeterminado)
/* 131:    */   {
/* 132:229 */     this.predeterminado = predeterminado;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public Bodega getBodega()
/* 136:    */   {
/* 137:238 */     return this.bodega;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setBodega(Bodega bodega)
/* 141:    */   {
/* 142:248 */     this.bodega = bodega;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public int getIdUbicacionBodega()
/* 146:    */   {
/* 147:257 */     return this.idUbicacionBodega;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setIdUbicacionBodega(int idUbicacionBodega)
/* 151:    */   {
/* 152:267 */     this.idUbicacionBodega = idUbicacionBodega;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public String toString()
/* 156:    */   {
/* 157:272 */     return this.codigo;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public int getId()
/* 161:    */   {
/* 162:277 */     return this.idUbicacionBodega;
/* 163:    */   }
/* 164:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.UbicacionBodega
 * JD-Core Version:    0.7.0.1
 */