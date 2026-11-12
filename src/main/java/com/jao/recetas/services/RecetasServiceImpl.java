package com.jao.recetas.services;
import com.jao.recetas.dto.CategoriaResponse;
import com.jao.recetas.dto.RecetaRequest;
import com.jao.recetas.dto.RecetaResponse;
import com.jao.recetas.model.Recetas;
import com.jao.recetas.repository.RecetasRepository;
import com.jao.recetas.util.exception.InvalidOperationException;
import com.jao.recetas.util.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RecetasServiceImpl implements RecetasService{
    private final RecetasRepository recetasRepository;
    private final CategoriasService categoriaService;


    @Override
    public Optional<RecetaResponse> findRecetaById(Integer id) {
        return this.recetasRepository.findRecetaById(id);
    }

    @Override
    public Optional<RecetaResponse> findOneByTitulo(String titulo) {
        return this.recetasRepository.findOneByTitulo(titulo);
    }

    @Override
    public List<RecetaResponse> findAllOrderByTituloAsc() {
        return this.recetasRepository.findAllOrderByTituloAsc();
    }

    @Override
    public List<RecetaResponse> findByNombreContainingIgnoreCaseOrderByTituloAsc(String infix) {
        return this.recetasRepository.findByNombreContainingIgnoreCaseOrderByTituloAsc(infix);
    }

    @Override
    public List<RecetaResponse> findByCategoriaIdOrderByTituloAsc(Integer categoriaId) {
        return this.recetasRepository.findByCategoriaIdOrderByTituloAsc(categoriaId);
    }

    @Override
    public long countByCategoriaId(Integer categoriaId) {
        return this.recetasRepository.countByCategoriaId(categoriaId);
    }

    @Override
    @Transactional
    public RecetaResponse createReceta(RecetaRequest request) {
        Optional<RecetaResponse> recetaDB = this.findOneByTitulo(request.getTitulo());
        if (recetaDB.isPresent()) {
            return recetaDB.get();
        }

        Optional<CategoriaResponse> categoriaDB = this.categoriaService.findCategoriaById(request.getCategoriaId());
        if (categoriaDB.isEmpty()) {
            throw new ResourceNotFoundException("No existe la categoría indicada, no se puede crear la receta");
        }

        Recetas receta = new Recetas(request);
        Recetas recetaNuevo = this.recetasRepository.save(receta);

        return new RecetaResponse(recetaNuevo);
    }

    @Override
    @Transactional
    public RecetaResponse updateReceta(Integer id, RecetaRequest request) {
        Optional<RecetaResponse> recetaDB = this.findRecetaById(id);
        if (recetaDB.isEmpty()) {
            throw new ResourceNotFoundException("No se puede efectuar la modificación, la receta no existe");
        }

        Optional<RecetaResponse> otraReceta = this.recetasRepository.findOneByTitulo(request.getTitulo());
        if (otraReceta.isPresent() && !recetaDB.get().getId().equals(otraReceta.get().getId())) {
            throw new InvalidOperationException("No se puede modificar el producto porque existe otro con el mismo nombre");
        }

        Optional<CategoriaResponse> categoriaDB = this.categoriaService.findCategoriaById(request.getCategoriaId());
        if (categoriaDB.isEmpty()) {
            throw new ResourceNotFoundException("No existe la categoría indicada, no se puede modificar el producto");
        }

        Recetas receta = new Recetas(recetaDB.get(), request);
        Recetas recetaModificado = this.recetasRepository.save(receta);

        return new RecetaResponse(recetaModificado);
    }

    @Override
    @Transactional
    public boolean deleteReceta(Integer id) {
        Optional<RecetaResponse> recetaDB = this.findRecetaById(id);
        if (recetaDB.isEmpty()) {
            return false;
        }

        this.recetasRepository.deleteById(id);

        return true;
    }


}
