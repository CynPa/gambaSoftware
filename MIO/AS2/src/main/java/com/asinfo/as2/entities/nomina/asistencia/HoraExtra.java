/*   1:    */ package com.asinfo.as2.entities.nomina.asistencia;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import com.asinfo.as2.entities.Rubro;
/*   5:    */ import com.asinfo.as2.enumeraciones.PorCientoHoraExtra;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.util.Date;
/*   8:    */ import javax.persistence.Column;
/*   9:    */ import javax.persistence.Entity;
/*  10:    */ import javax.persistence.EnumType;
/*  11:    */ import javax.persistence.Enumerated;
/*  12:    */ import javax.persistence.FetchType;
/*  13:    */ import javax.persistence.GeneratedValue;
/*  14:    */ import javax.persistence.GenerationType;
/*  15:    */ import javax.persistence.Id;
/*  16:    */ import javax.persistence.JoinColumn;
/*  17:    */ import javax.persistence.ManyToOne;
/*  18:    */ import javax.persistence.Table;
/*  19:    */ import javax.persistence.TableGenerator;
/*  20:    */ import javax.persistence.Temporal;
/*  21:    */ import javax.persistence.TemporalType;
/*  22:    */ import javax.validation.constraints.NotNull;
/*  23:    */ import javax.validation.constraints.Size;
/*  24:    */ import org.hibernate.annotations.ColumnDefault;
/*  25:    */ 
/*  26:    */ @Entity
/*  27:    */ @Table(name="hora_extra", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  28:    */ public class HoraExtra
/*  29:    */   extends EntidadBase
/*  30:    */   implements Serializable
/*  31:    */ {
/*  32:    */   private static final long serialVersionUID = 1L;
/*  33:    */   @Id
/*  34:    */   @TableGenerator(name="hora_extra", initialValue=0, allocationSize=50)
/*  35:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="hora_extra")
/*  36:    */   @Column(name="id_hora_extra", unique=true, nullable=false)
/*  37:    */   private int idHoraExtra;
/*  38:    */   @Column(name="id_organizacion", nullable=false)
/*  39:    */   private int idOrganizacion;
/*  40:    */   @Column(name="id_sucursal", nullable=false)
/*  41:    */   private int idSucursal;
/*  42:    */   @Column(name="codigo", nullable=false, length=10)
/*  43:    */   @NotNull
/*  44:    */   @Size(min=2, max=10)
/*  45:    */   private String codigo;
/*  46:    */   @Column(name="nombre", nullable=false, length=50)
/*  47:    */   @NotNull
/*  48:    */   @Size(min=2, max=50)
/*  49:    */   private String nombre;
/*  50:    */   @Column(name="descripcion", nullable=true, length=200)
/*  51:    */   @Size(max=200)
/*  52:    */   private String descripcion;
/*  53:    */   @Column(name="indicador_dentro_horario", nullable=false)
/*  54:    */   private boolean indicadorDentroHorario;
/*  55:    */   @Column(name="indicador_dia_festivo", nullable=false)
/*  56:    */   private boolean indicadorDiaFestivo;
/*  57:    */   @Column(name="indicador_dia_descanso", nullable=false)
/*  58:    */   private boolean indicadorDiaDescanso;
/*  59:    */   @Temporal(TemporalType.TIME)
/*  60:    */   @Column(name="hora_desde", nullable=false)
/*  61:    */   private Date horaDesde;
/*  62:    */   @Temporal(TemporalType.TIME)
/*  63:    */   @Column(name="hora_hasta", nullable=false)
/*  64:    */   private Date horaHasta;
/*  65:    */   @Column(name="por_ciento", nullable=false)
/*  66:    */   @NotNull
/*  67:    */   @Enumerated(EnumType.ORDINAL)
/*  68:    */   private PorCientoHoraExtra porCiento;
/*  69:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  70:    */   @JoinColumn(name="id_rubro", nullable=false)
/*  71:    */   @NotNull
/*  72:    */   private Rubro rubro;
/*  73:    */   @Column(name="indicador_dia_complementario", nullable=false)
/*  74:    */   @NotNull
/*  75:    */   @ColumnDefault("'0'")
/*  76:107 */   private Boolean indicadorDiaComplementario = Boolean.valueOf(false);
/*  77:    */   
/*  78:    */   public int getId()
/*  79:    */   {
/*  80:114 */     return this.idHoraExtra;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public int getIdHoraExtra()
/*  84:    */   {
/*  85:118 */     return this.idHoraExtra;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setIdHoraExtra(int idHoraExtra)
/*  89:    */   {
/*  90:122 */     this.idHoraExtra = idHoraExtra;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public int getIdOrganizacion()
/*  94:    */   {
/*  95:131 */     return this.idOrganizacion;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setIdOrganizacion(int idOrganizacion)
/*  99:    */   {
/* 100:141 */     this.idOrganizacion = idOrganizacion;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public int getIdSucursal()
/* 104:    */   {
/* 105:150 */     return this.idSucursal;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setIdSucursal(int idSucursal)
/* 109:    */   {
/* 110:160 */     this.idSucursal = idSucursal;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String getCodigo()
/* 114:    */   {
/* 115:169 */     return this.codigo;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setCodigo(String codigo)
/* 119:    */   {
/* 120:179 */     this.codigo = codigo;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public String getNombre()
/* 124:    */   {
/* 125:188 */     return this.nombre;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setNombre(String nombre)
/* 129:    */   {
/* 130:198 */     this.nombre = nombre;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public String getDescripcion()
/* 134:    */   {
/* 135:207 */     return this.descripcion;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setDescripcion(String descripcion)
/* 139:    */   {
/* 140:217 */     this.descripcion = descripcion;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public boolean isIndicadorDentroHorario()
/* 144:    */   {
/* 145:221 */     return this.indicadorDentroHorario;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setIndicadorDentroHorario(boolean indicadorDentroHorario)
/* 149:    */   {
/* 150:225 */     this.indicadorDentroHorario = indicadorDentroHorario;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public boolean isIndicadorDiaFestivo()
/* 154:    */   {
/* 155:229 */     return this.indicadorDiaFestivo;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void setIndicadorDiaFestivo(boolean indicadorDiaFestivo)
/* 159:    */   {
/* 160:233 */     this.indicadorDiaFestivo = indicadorDiaFestivo;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public Date getHoraDesde()
/* 164:    */   {
/* 165:237 */     return this.horaDesde;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void setHoraDesde(Date horaDesde)
/* 169:    */   {
/* 170:241 */     this.horaDesde = horaDesde;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public Date getHoraHasta()
/* 174:    */   {
/* 175:245 */     return this.horaHasta;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void setHoraHasta(Date horaHasta)
/* 179:    */   {
/* 180:249 */     this.horaHasta = horaHasta;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public PorCientoHoraExtra getPorCiento()
/* 184:    */   {
/* 185:253 */     return this.porCiento;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void setPorCiento(PorCientoHoraExtra porCiento)
/* 189:    */   {
/* 190:257 */     this.porCiento = porCiento;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public Rubro getRubro()
/* 194:    */   {
/* 195:261 */     return this.rubro;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void setRubro(Rubro rubro)
/* 199:    */   {
/* 200:265 */     this.rubro = rubro;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public String toString()
/* 204:    */   {
/* 205:270 */     return this.nombre;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public boolean isIndicadorDiaDescanso()
/* 209:    */   {
/* 210:274 */     return this.indicadorDiaDescanso;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setIndicadorDiaDescanso(boolean indicadorDiaDescanso)
/* 214:    */   {
/* 215:278 */     this.indicadorDiaDescanso = indicadorDiaDescanso;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public Boolean getIndicadorDiaComplementario()
/* 219:    */   {
/* 220:282 */     if (this.indicadorDiaComplementario == null) {
/* 221:283 */       this.indicadorDiaComplementario = Boolean.valueOf(false);
/* 222:    */     }
/* 223:284 */     return this.indicadorDiaComplementario;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public void setIndicadorDiaComplementario(Boolean indicadorDiaComplementario)
/* 227:    */   {
/* 228:288 */     this.indicadorDiaComplementario = indicadorDiaComplementario;
/* 229:    */   }
/* 230:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.nomina.asistencia.HoraExtra
 * JD-Core Version:    0.7.0.1
 */