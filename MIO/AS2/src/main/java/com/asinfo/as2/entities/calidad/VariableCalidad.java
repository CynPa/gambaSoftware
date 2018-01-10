/*   1:    */ package com.asinfo.as2.entities.calidad;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.FetchType;
/*   9:    */ import javax.persistence.GeneratedValue;
/*  10:    */ import javax.persistence.GenerationType;
/*  11:    */ import javax.persistence.Id;
/*  12:    */ import javax.persistence.JoinColumn;
/*  13:    */ import javax.persistence.ManyToOne;
/*  14:    */ import javax.persistence.Table;
/*  15:    */ import javax.persistence.TableGenerator;
/*  16:    */ import javax.persistence.Transient;
/*  17:    */ import javax.validation.constraints.NotNull;
/*  18:    */ import javax.validation.constraints.Size;
/*  19:    */ import org.hibernate.annotations.ColumnDefault;
/*  20:    */ 
/*  21:    */ @Entity
/*  22:    */ @Table(name="variable_calidad", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  23:    */ public class VariableCalidad
/*  24:    */   extends EntidadBase
/*  25:    */   implements Serializable
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 1L;
/*  28:    */   @Id
/*  29:    */   @TableGenerator(name="variable_calidad", initialValue=0, allocationSize=50)
/*  30:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="variable_calidad")
/*  31:    */   @Column(name="id_variable_calidad")
/*  32:    */   private int idVariableCalidad;
/*  33:    */   @Column(name="id_organizacion")
/*  34:    */   private int idOrganizacion;
/*  35:    */   @Column(name="id_sucursal")
/*  36:    */   private int idSucursal;
/*  37:    */   @Column(name="codigo", nullable=false, length=20)
/*  38:    */   @NotNull
/*  39:    */   @Size(min=1, max=20)
/*  40:    */   private String codigo;
/*  41:    */   @Column(name="nombre", nullable=false, length=100)
/*  42:    */   @NotNull
/*  43:    */   @Size(min=2, max=100)
/*  44:    */   private String nombre;
/*  45:    */   @Column(name="predeterminado", nullable=false)
/*  46:    */   private boolean predeterminado;
/*  47:    */   @Column(name="activo", nullable=false)
/*  48:    */   private boolean activo;
/*  49:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  50:    */   @JoinColumn(name="id_categoria_variable_calidad", nullable=false)
/*  51:    */   @NotNull
/*  52:    */   private CategoriaVariableCalidad categoriaVariableCalidad;
/*  53:    */   @Column(name="indicador_automatica", nullable=false)
/*  54:    */   @NotNull
/*  55:    */   @ColumnDefault("'0'")
/*  56:    */   private boolean indicadorAutomatica;
/*  57:    */   @Column(name="indicador_materia_prima", nullable=false)
/*  58:    */   @NotNull
/*  59:    */   @ColumnDefault("'1'")
/*  60:    */   private boolean indicadorMateriaPrima;
/*  61:    */   @Column(name="indicador_producto_terminado", nullable=false)
/*  62:    */   @NotNull
/*  63:    */   @ColumnDefault("'0'")
/*  64:    */   private boolean indicadorProductoTerminado;
/*  65:    */   @Column(name="indicador_visualizar", nullable=false)
/*  66:    */   @NotNull
/*  67:    */   @ColumnDefault("'1'")
/*  68:    */   private boolean indicadorVisualizar;
/*  69:    */   @Column(name="indicador_medicion_unica", nullable=false)
/*  70:    */   @NotNull
/*  71:    */   @ColumnDefault("'0'")
/*  72:    */   private boolean indicadorMedicionUnica;
/*  73:    */   @Transient
/*  74: 99 */   private BigDecimal valorNirPromedio = BigDecimal.ZERO;
/*  75:    */   
/*  76:    */   public VariableCalidad() {}
/*  77:    */   
/*  78:    */   public VariableCalidad(int idVariableCalidad, Double valorNirPromedio)
/*  79:    */   {
/*  80:110 */     this.idVariableCalidad = idVariableCalidad;
/*  81:111 */     this.valorNirPromedio = new BigDecimal(valorNirPromedio.doubleValue());
/*  82:    */   }
/*  83:    */   
/*  84:    */   public int getId()
/*  85:    */   {
/*  86:118 */     return this.idVariableCalidad;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public int getIdVariableCalidad()
/*  90:    */   {
/*  91:125 */     return this.idVariableCalidad;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setIdVariableCalidad(int idVariableCalidad)
/*  95:    */   {
/*  96:133 */     this.idVariableCalidad = idVariableCalidad;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public int getIdOrganizacion()
/* 100:    */   {
/* 101:140 */     return this.idOrganizacion;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setIdOrganizacion(int idOrganizacion)
/* 105:    */   {
/* 106:148 */     this.idOrganizacion = idOrganizacion;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public int getIdSucursal()
/* 110:    */   {
/* 111:155 */     return this.idSucursal;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setIdSucursal(int idSucursal)
/* 115:    */   {
/* 116:163 */     this.idSucursal = idSucursal;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public String getCodigo()
/* 120:    */   {
/* 121:170 */     return this.codigo;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setCodigo(String codigo)
/* 125:    */   {
/* 126:178 */     this.codigo = codigo;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public String getNombre()
/* 130:    */   {
/* 131:185 */     return this.nombre;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void setNombre(String nombre)
/* 135:    */   {
/* 136:193 */     this.nombre = nombre;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public boolean isPredeterminado()
/* 140:    */   {
/* 141:200 */     return this.predeterminado;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void setPredeterminado(boolean predeterminado)
/* 145:    */   {
/* 146:208 */     this.predeterminado = predeterminado;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public boolean isActivo()
/* 150:    */   {
/* 151:215 */     return this.activo;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setActivo(boolean activo)
/* 155:    */   {
/* 156:223 */     this.activo = activo;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public CategoriaVariableCalidad getCategoriaVariableCalidad()
/* 160:    */   {
/* 161:230 */     return this.categoriaVariableCalidad;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void setCategoriaVariableCalidad(CategoriaVariableCalidad categoriaVariableCalidad)
/* 165:    */   {
/* 166:238 */     this.categoriaVariableCalidad = categoriaVariableCalidad;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public boolean isIndicadorAutomatica()
/* 170:    */   {
/* 171:242 */     return this.indicadorAutomatica;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setIndicadorAutomatica(boolean indicadorAutomatica)
/* 175:    */   {
/* 176:246 */     this.indicadorAutomatica = indicadorAutomatica;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public boolean isIndicadorMateriaPrima()
/* 180:    */   {
/* 181:250 */     return this.indicadorMateriaPrima;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void setIndicadorMateriaPrima(boolean indicadorMateriaPrima)
/* 185:    */   {
/* 186:254 */     this.indicadorMateriaPrima = indicadorMateriaPrima;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public boolean isIndicadorProductoTerminado()
/* 190:    */   {
/* 191:258 */     return this.indicadorProductoTerminado;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void setIndicadorProductoTerminado(boolean indicadorProductoTerminado)
/* 195:    */   {
/* 196:262 */     this.indicadorProductoTerminado = indicadorProductoTerminado;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public BigDecimal getValorNirPromedio()
/* 200:    */   {
/* 201:266 */     return this.valorNirPromedio;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void setValorNirPromedio(BigDecimal valorNirPromedio)
/* 205:    */   {
/* 206:270 */     this.valorNirPromedio = valorNirPromedio;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public boolean isIndicadorVisualizar()
/* 210:    */   {
/* 211:274 */     return this.indicadorVisualizar;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void setIndicadorVisualizar(boolean indicadorVisualizar)
/* 215:    */   {
/* 216:278 */     this.indicadorVisualizar = indicadorVisualizar;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public boolean isIndicadorMedicionUnica()
/* 220:    */   {
/* 221:282 */     return this.indicadorMedicionUnica;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public void setIndicadorMedicionUnica(boolean indicadorMedicionUnica)
/* 225:    */   {
/* 226:286 */     this.indicadorMedicionUnica = indicadorMedicionUnica;
/* 227:    */   }
/* 228:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.calidad.VariableCalidad
 * JD-Core Version:    0.7.0.1
 */