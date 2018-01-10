/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.GeneratedValue;
/*   7:    */ import javax.persistence.GenerationType;
/*   8:    */ import javax.persistence.Id;
/*   9:    */ import javax.persistence.Table;
/*  10:    */ import javax.persistence.TableGenerator;
/*  11:    */ import javax.validation.constraints.Max;
/*  12:    */ import javax.validation.constraints.Min;
/*  13:    */ import javax.validation.constraints.NotNull;
/*  14:    */ import javax.validation.constraints.Size;
/*  15:    */ 
/*  16:    */ @Entity
/*  17:    */ @Table(name="nivel_centro_costo", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  18:    */ public class NivelCentroCosto
/*  19:    */   extends EntidadBase
/*  20:    */   implements Serializable
/*  21:    */ {
/*  22:    */   private static final long serialVersionUID = 3003904189915547712L;
/*  23:    */   @Id
/*  24:    */   @TableGenerator(name="nivel_centro_costo", initialValue=0, allocationSize=50)
/*  25:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="nivel_centro_costo")
/*  26:    */   @Column(name="id_nivel_centro_costo")
/*  27:    */   private int idNivelCentroCosto;
/*  28:    */   @Column(name="id_organizacion")
/*  29:    */   private int idOrganizacion;
/*  30:    */   @Column(name="id_sucursal")
/*  31:    */   private int idSucursal;
/*  32:    */   @Column(name="longitud", nullable=false)
/*  33:    */   @NotNull
/*  34:    */   private int longitud;
/*  35:    */   @Column(name="codigo", nullable=false)
/*  36:    */   @Min(1L)
/*  37:    */   @Max(9L)
/*  38:    */   private int codigo;
/*  39:    */   @Column(name="nombre", length=50, nullable=false)
/*  40:    */   @Size(min=2, max=50)
/*  41:    */   @NotNull
/*  42:    */   private String nombre;
/*  43:    */   @Column(name="descripcion", length=200)
/*  44:    */   @Size(max=200)
/*  45:    */   private String descripcion;
/*  46:    */   @Column(name="activo", nullable=false)
/*  47:    */   private boolean activo;
/*  48:    */   @Column(name="predeterminado", nullable=false)
/*  49:    */   private boolean predeterminado;
/*  50:    */   @Column(name="indicador_movimiento", nullable=false)
/*  51:    */   private boolean indicadorMovimiento;
/*  52:    */   
/*  53:    */   public NivelCentroCosto() {}
/*  54:    */   
/*  55:    */   public NivelCentroCosto(int idNivelCentroCosto, int codigo, String nombre)
/*  56:    */   {
/*  57: 94 */     this.idNivelCentroCosto = idNivelCentroCosto;
/*  58: 95 */     this.codigo = codigo;
/*  59: 96 */     this.nombre = nombre;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public int getId()
/*  63:    */   {
/*  64:106 */     return this.idNivelCentroCosto;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public int getIdNivelCentroCosto()
/*  68:    */   {
/*  69:117 */     return this.idNivelCentroCosto;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setIdNivelCentroCosto(int idNivelCentroCosto)
/*  73:    */   {
/*  74:127 */     this.idNivelCentroCosto = idNivelCentroCosto;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public int getIdOrganizacion()
/*  78:    */   {
/*  79:136 */     return this.idOrganizacion;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setIdOrganizacion(int idOrganizacion)
/*  83:    */   {
/*  84:146 */     this.idOrganizacion = idOrganizacion;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public int getIdSucursal()
/*  88:    */   {
/*  89:155 */     return this.idSucursal;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setIdSucursal(int idSucursal)
/*  93:    */   {
/*  94:165 */     this.idSucursal = idSucursal;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public int getLongitud()
/*  98:    */   {
/*  99:174 */     return this.longitud;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setLongitud(int longitud)
/* 103:    */   {
/* 104:184 */     this.longitud = longitud;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public int getCodigo()
/* 108:    */   {
/* 109:193 */     return this.codigo;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setCodigo(int codigo)
/* 113:    */   {
/* 114:203 */     this.codigo = codigo;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public String getNombre()
/* 118:    */   {
/* 119:212 */     return this.nombre;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setNombre(String nombre)
/* 123:    */   {
/* 124:222 */     this.nombre = nombre;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public String getDescripcion()
/* 128:    */   {
/* 129:231 */     return this.descripcion;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setDescripcion(String descripcion)
/* 133:    */   {
/* 134:241 */     this.descripcion = descripcion;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public boolean isActivo()
/* 138:    */   {
/* 139:250 */     return this.activo;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setActivo(boolean activo)
/* 143:    */   {
/* 144:260 */     this.activo = activo;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public boolean isPredeterminado()
/* 148:    */   {
/* 149:269 */     return this.predeterminado;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setPredeterminado(boolean predeterminado)
/* 153:    */   {
/* 154:279 */     this.predeterminado = predeterminado;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public boolean isIndicadorMovimiento()
/* 158:    */   {
/* 159:288 */     return this.indicadorMovimiento;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setIndicadorMovimiento(boolean indicadorMovimiento)
/* 163:    */   {
/* 164:298 */     this.indicadorMovimiento = indicadorMovimiento;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public String toString()
/* 168:    */   {
/* 169:306 */     return this.nombre = this.nombre == null ? "no definido" : this.nombre;
/* 170:    */   }
/* 171:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.NivelCentroCosto
 * JD-Core Version:    0.7.0.1
 */