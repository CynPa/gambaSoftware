/*   1:    */ package com.asinfo.as2.entities.sri;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.FetchType;
/*   8:    */ import javax.persistence.GeneratedValue;
/*   9:    */ import javax.persistence.GenerationType;
/*  10:    */ import javax.persistence.Id;
/*  11:    */ import javax.persistence.JoinColumn;
/*  12:    */ import javax.persistence.ManyToOne;
/*  13:    */ import javax.persistence.Table;
/*  14:    */ import javax.persistence.TableGenerator;
/*  15:    */ import javax.validation.constraints.NotNull;
/*  16:    */ import javax.validation.constraints.Size;
/*  17:    */ import org.hibernate.annotations.ColumnDefault;
/*  18:    */ 
/*  19:    */ @Entity
/*  20:    */ @Table(name="ibp_marca", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  21:    */ public class IBPMarca
/*  22:    */   extends EntidadBase
/*  23:    */   implements Serializable
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = 1L;
/*  26:    */   @Id
/*  27:    */   @TableGenerator(name="ibp_marca", initialValue=0, allocationSize=50)
/*  28:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="ibp_marca")
/*  29:    */   @Column(name="id_ibp_marca", unique=true, nullable=false)
/*  30:    */   private int idIBPMarca;
/*  31:    */   @Column(name="id_organizacion", nullable=false)
/*  32:    */   private int idOrganizacion;
/*  33:    */   @Column(name="id_sucursal", nullable=false)
/*  34:    */   private int idSucursal;
/*  35:    */   @Column(name="codigo", nullable=false, length=6)
/*  36:    */   @NotNull
/*  37:    */   @Size(min=6, max=6)
/*  38:    */   private String codigo;
/*  39:    */   @Column(name="codigo_ice", length=6, nullable=false)
/*  40:    */   @ColumnDefault("'000000'")
/*  41:    */   @NotNull
/*  42:    */   @Size(min=6, max=6)
/*  43: 61 */   private String codigoIce = "000000";
/*  44:    */   @Column(name="nombre", nullable=false, length=100)
/*  45:    */   @NotNull
/*  46:    */   @Size(min=1, max=100)
/*  47:    */   private String nombre;
/*  48:    */   @Column(name="descripcion", nullable=true, length=300)
/*  49:    */   @Size(max=300)
/*  50:    */   private String descripcion;
/*  51:    */   @Column(name="activo", nullable=false)
/*  52:    */   @NotNull
/*  53: 76 */   private boolean activo = true;
/*  54:    */   @Column(name="predeterminado", nullable=false)
/*  55:    */   @NotNull
/*  56:    */   private boolean predeterminado;
/*  57:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  58:    */   @JoinColumn(name="id_ibp_clasificacion", nullable=false)
/*  59:    */   @NotNull
/*  60:    */   private IBPClasificacion ibpClasificacion;
/*  61:    */   
/*  62:    */   public IBPMarca() {}
/*  63:    */   
/*  64:    */   public IBPMarca(int idIBPMarca, String codigo, String nombre)
/*  65:    */   {
/*  66: 99 */     this.idIBPMarca = idIBPMarca;
/*  67:100 */     this.codigo = codigo;
/*  68:101 */     this.nombre = nombre;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int getId()
/*  72:    */   {
/*  73:106 */     return this.idIBPMarca;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public int getIdIBPMarca()
/*  77:    */   {
/*  78:115 */     return this.idIBPMarca;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setIdIBPMarca(int idIBPMarca)
/*  82:    */   {
/*  83:125 */     this.idIBPMarca = idIBPMarca;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public int getIdOrganizacion()
/*  87:    */   {
/*  88:134 */     return this.idOrganizacion;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setIdOrganizacion(int idOrganizacion)
/*  92:    */   {
/*  93:144 */     this.idOrganizacion = idOrganizacion;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public int getIdSucursal()
/*  97:    */   {
/*  98:153 */     return this.idSucursal;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setIdSucursal(int idSucursal)
/* 102:    */   {
/* 103:163 */     this.idSucursal = idSucursal;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String getCodigo()
/* 107:    */   {
/* 108:172 */     return this.codigo;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setCodigo(String codigo)
/* 112:    */   {
/* 113:182 */     this.codigo = codigo;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String getNombre()
/* 117:    */   {
/* 118:191 */     return this.nombre;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setNombre(String nombre)
/* 122:    */   {
/* 123:201 */     this.nombre = nombre;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public String getDescripcion()
/* 127:    */   {
/* 128:210 */     return this.descripcion;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setDescripcion(String descripcion)
/* 132:    */   {
/* 133:220 */     this.descripcion = descripcion;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public boolean isActivo()
/* 137:    */   {
/* 138:229 */     return this.activo;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setActivo(boolean activo)
/* 142:    */   {
/* 143:239 */     this.activo = activo;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public boolean isPredeterminado()
/* 147:    */   {
/* 148:248 */     return this.predeterminado;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setPredeterminado(boolean predeterminado)
/* 152:    */   {
/* 153:258 */     this.predeterminado = predeterminado;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public IBPClasificacion getIbpClasificacion()
/* 157:    */   {
/* 158:262 */     return this.ibpClasificacion;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setIbpClasificacion(IBPClasificacion ibpClasificacion)
/* 162:    */   {
/* 163:266 */     this.ibpClasificacion = ibpClasificacion;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public String getCodigoIce()
/* 167:    */   {
/* 168:270 */     return this.codigoIce;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setCodigoIce(String codigoIce)
/* 172:    */   {
/* 173:274 */     this.codigoIce = codigoIce;
/* 174:    */   }
/* 175:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.sri.IBPMarca
 * JD-Core Version:    0.7.0.1
 */